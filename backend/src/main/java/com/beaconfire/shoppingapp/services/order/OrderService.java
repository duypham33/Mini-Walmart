package com.beaconfire.shoppingapp.services.order;

import com.beaconfire.shoppingapp.daos.order.OrderDao;
import com.beaconfire.shoppingapp.daos.product.ProductDao;
import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate.OrderTemplateItemDto;
import com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate.OrderTemplatePreparedItemsDto;
import com.beaconfire.shoppingapp.dtos.order.*;
import com.beaconfire.shoppingapp.dtos.queryFeature.QueryPage;
import com.beaconfire.shoppingapp.entities.account.subscription.AccountSubscription;
import com.beaconfire.shoppingapp.entities.order.Invoice;
import com.beaconfire.shoppingapp.entities.order.Order;
import com.beaconfire.shoppingapp.entities.order.Shipping;
import com.beaconfire.shoppingapp.entities.product.Product;
import com.beaconfire.shoppingapp.services.account.SubscriptionService;
import com.beaconfire.shoppingapp.services.account.UserService;
import com.beaconfire.shoppingapp.utils.OrderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class OrderService {
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final SubscriptionService subscriptionService;
    private final UserService userService;

    public OrderService(
            OrderDao orderDao, ProductDao productDao, UserService userService, SubscriptionService subscriptionService
    ){
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    public ResponseDto<List<OrderDetailDto>> searchOrders(
            Integer page, Integer pageSize, String status
    ){
        List<OrderDetailDto> orders = orderDao.getOrdersForSeller(
                QueryPage.builder().page(page).pageSize(pageSize).build(), !status.equals("ALL") ? Order.Status.valueOf(status) : null
        ).stream().map(OrderUtil::toOrderDetailDtoForSeller).toList();

        String message = !orders.isEmpty() ? String.format("Found %d orders for page %d", orders.size(), page)
                : "No records found!";
        return ResponseDto.get(orders, message);
    }

    public ResponseDto<OrderDetailDto> getOrderDetails(Long orderId, Boolean forSeller){
        Order order = orderDao.getOrderById(orderId);
        if(order == null) return ResponseDto.get(null, "Not found order with id " + orderId, HttpStatus.NOT_FOUND);
        return ResponseDto.get(forSeller ? OrderUtil.toOrderDetailDtoForSeller(order) : OrderUtil.toOrderDetailDto(order));
    }

    public ResponseDto<List<OrderDetailDto>> getOrdersByUserId(Long userId, Integer page, Integer pageSize, String status){
        return ResponseDto.get(orderDao.getOrdersByUserId(
                    userId, QueryPage.builder().page(page).pageSize(pageSize).build(), !status.equals("ALL") ? Order.Status.valueOf(status) : null
                )
                .stream().map(OrderUtil::toOrderDetailDto).toList());
    }

    public ResponseDto<OrderPreviewDto> previewOrderFromCart(Long userId){
        // Prepare buying items from shopping cart
        var buyingItems = userService.getCartItems(userId).stream().map(BuyingItemDto::fromCartItem).toList();
        if(buyingItems.isEmpty()) return ResponseDto.get(null, "Your shopping cart is empty!");

        return previewOrder(userId, buyingItems, new ArrayList<>());
    }

    public ResponseDto<OrderPreviewDto> previewOrderFromOrderTemplate(Long userId, Long templateId){
        // Prepare buying items from the order template
        var res = selectProductsForOrderTemplate(templateId);
        if(res == null) return ResponseDto.get(null, "Not found order template!");
        if(res.getBuyingItems().isEmpty()) return ResponseDto.get(null, "Your order template is empty!");

        return previewOrder(userId, res.getBuyingItems(), res.getRecordLog());
    }

    public ResponseDto<OrderDetailDto> placeOrderFromCart(Long userId){
        // Getting items from the shopping cart
        var buyingItems = userService.getCartItems(userId).stream().map(BuyingItemDto::fromCartItem).toList();
        if(buyingItems.isEmpty()) return ResponseDto.get(null, "Your shopping cart is empty!");

        // Remove cart items
        Consumer<Long> clearCart = (orderId) -> userService.clearCart(userId);

        return placeOrder(userId, buyingItems, "", clearCart);
    }

    public ResponseDto<OrderDetailDto> placeOrderFromOrderTemplate(Long userId, Long templateId){
        // Prepare buying items from the order template
        var res = selectProductsForOrderTemplate(templateId);
        if(res == null) return ResponseDto.get(null, "Not found order template!");
        if(res.getBuyingItems().isEmpty()) return ResponseDto.get(null, "Your order template is empty!");

        // record template usages as well as effectivity of a template
        Consumer<Long> templateToOrder = (orderId) -> userService.createTemplateToOrder(orderId, templateId);

        String record = res.getRecordLog().isEmpty() ? "" : String.join("\n", res.getRecordLog());
        return placeOrder(userId, res.getBuyingItems(), record, templateToOrder);
    }

    public ResponseDto<OrderDetailDto> cancelOrder(Long orderId, Boolean returnByECash, Boolean bySeller){
        Order order = orderDao.getOrderById(orderId);
        if(order == null) return ResponseDto.get(null, "Not found order with id " + orderId, HttpStatus.NOT_FOUND);
        if(order.getStatus().equals(Order.Status.CANCEL) || order.getStatus().equals(Order.Status.COMPLETED)){
            return ResponseDto.get(null, "The order was previously " + order.getStatus(), HttpStatus.BAD_REQUEST);
        }

        String recordedLog = order.getRecordedLog() + "\n" + OrderUtil.cancelRecordLog(returnByECash);
        String message = String.format("Successfully cancel the order, %s would get the refund soon!", bySeller ? "the customer" : "you");
        if(returnByECash != null && returnByECash){
            float dueAmount = order.getTotal();
            userService.updateECash(order.getUser().getId(), dueAmount);
            message = String.format("Successfully cancel the order, we added $%.2f into %s E-Cash", dueAmount, bySeller ? "the customer's" : "your");
        }

        // Update into database
        orderDao.updateOrder(orderId, Order.Status.CANCEL, recordedLog);
        order.setStatus(Order.Status.CANCEL);
        order.setRecordedLog(recordedLog);

        // Increase stock
        for(var item : order.getItems()){
            var p = item.getProduct();
            productDao.updateProductQuantity(p.getId(), p.getQuantity() + item.getQuantity());
        }

        return ResponseDto.get(
                OrderUtil.toOrderDetailDto(order), message
        );
    }

    public ResponseDto<OrderDetailDto> completeOrder(Long orderId){
        Order order = orderDao.getOrderById(orderId);
        if(order == null) return ResponseDto.get(null, "Not found order with id " + orderId, HttpStatus.NOT_FOUND);
        if(order.getStatus().equals(Order.Status.CANCEL) || order.getStatus().equals(Order.Status.COMPLETED)){
            return ResponseDto.get(null, "The order was previously " + order.getStatus(), HttpStatus.BAD_REQUEST);
        }

        String recordedLog = order.getRecordedLog() + "\n" + "Order completed";
        String message = "Successfully completed the order!";

        // Update order in database
        orderDao.updateOrder(orderId, Order.Status.COMPLETED, recordedLog);
        order.setStatus(Order.Status.COMPLETED);
        order.setRecordedLog(recordedLog);

        // Update shipping in database
        orderDao.updateShipping(order.getShipping().getId(), Shipping.Status.DELIVERED);
        order.getShipping().setStatus(Shipping.Status.DELIVERED);

        // Update order items in database
        orderDao.updateOrderItemsStatus(orderId, true);

        return ResponseDto.get(
                OrderUtil.toOrderDetailDto(order), message
        );
    }

    private OrderTemplatePreparedItemsDto selectProductsForOrderTemplate(Long templateId){
        var template = userService.viewOrderTemplate(templateId).getData();
        if(template == null) return null;

        List<BuyingItemDto> buyingItems = new ArrayList<>();
        List<String> recordLog = new ArrayList<>();
        for(OrderTemplateItemDto item : template.getItems()){
            var product = productDao.findProductById(item.getProduct().getId());
            if(product.getQuantity() >= item.getQuantity()){
                buyingItems.add(BuyingItemDto.fromOrderTemplateItem(item, product));
                continue;
            }

            int remaining = item.getQuantity();
            if(product.getQuantity() > 0){
                remaining = item.getQuantity() - product.getQuantity();
                item.setQuantity(product.getQuantity());
                buyingItems.add(BuyingItemDto.fromOrderTemplateItem(item, product));
            }

            // Find substitution
            var preference = item.getSubstitutionPreference();
            Product substitution = null;
            if(preference != null){
                var preferenceProduct = productDao.findProductById(preference.getId());
                if(preferenceProduct != null && preferenceProduct.getQuantity() > 0)
                    substitution = preferenceProduct;
            }

            if(substitution == null) substitution = productDao.findClosestProduct(product);
            buyingItems.add(
                    OrderUtil.buyingItemFromSubstitution(item.getId(), substitution, remaining)
            );
            recordLog.add(OrderUtil.substitutionRecordLog(product.getName(), substitution, remaining));
        }

        return OrderTemplatePreparedItemsDto.builder()
                .buyingItems(buyingItems).recordLog(recordLog)
                .build();
    }


    private ResponseDto<OrderPreviewDto> previewOrder(
            Long userId, List<BuyingItemDto> buyingItems, List<String> recordLog
    ){
        List<InvoiceItemDto> invoiceItems = new ArrayList<>(buyingItems.stream().map(OrderUtil::toInvoiceItemDtoFromBuyingItem).toList());

        // Original cost
        final Float originalTotal = OrderUtil.getInvoiceTotal(invoiceItems);

        // Generate shipping and create invoice item for shipping
        var shippingDto = OrderUtil.estimateShipping(buyingItems.stream().map(BuyingItemDto::getQuantity).reduce(0, Integer::sum));
        invoiceItems.add(OrderUtil.toInvoiceItemDtoFromShipping(shippingDto.getFee()));

        // Cost with shipping
        final Float shippingIncludedCost = originalTotal + shippingDto.getFee();

        // Apply account subscription features
        var subscription = subscriptionService.getUserSubscription(userId).getData();
        if(subscription != null && subscription.getStatus().equals(AccountSubscription.SubscriptionStatus.ACTIVE)){
            // Free shipping feature
            var freeShippingInvoiceItem = OrderUtil.createInvoiceItemForFreeShippingFeature(originalTotal, shippingDto.getFee(), subscription);
            if(freeShippingInvoiceItem != null) invoiceItems.add(freeShippingInvoiceItem);

            // Discount feature
            var discountInvoiceItem = OrderUtil.createInvoiceItemForDiscountFeature(originalTotal, subscription);
            if(discountInvoiceItem != null) invoiceItems.add(discountInvoiceItem);
        }

        // Final cost
        final Float finalTotal = OrderUtil.getInvoiceTotal(invoiceItems);

        // Compute savings
        final Float saving = shippingIncludedCost - finalTotal;

        // Check user e-cash
        final Float cash = userService.getECash(userId).getData();
        if(cash > 0){
            var eCashInvoiceItem = OrderUtil.createInvoiceItemForCash(cash, finalTotal);
            if(eCashInvoiceItem != null) invoiceItems.add(eCashInvoiceItem);
        }

        // Due cost (have to pay, after using e-cash)
        final Float dueAmount = originalTotal - finalTotal;

        return ResponseDto.get(OrderUtil.createOrderPreview(shippingDto, invoiceItems, saving, subscription, recordLog));
    }


    public ResponseDto<OrderDetailDto> placeOrder(
            Long userId, List<BuyingItemDto> buyingItems, String recordedLog, Consumer<Long> lastProcess
    ){
        // Checking product stocks, and will throw NotEnoughInventoryException exception if out of stock
        OrderUtil.checkStock(buyingItems);

        // Deduct stock
        for(var item : buyingItems){
            productDao.updateProductQuantity(item.getProductId(), item.getInStock() - item.getQuantity());
        }

        // Create invoice
        Invoice invoice = orderDao.createInvoice();
        // Create dto invoice items for the buying items
        List<InvoiceItemDto> invoiceItems = new ArrayList<>(buyingItems.stream().map(OrderUtil::toInvoiceItemDtoFromBuyingItem).toList());
        // Original cost
        final Float originalTotal = OrderUtil.getInvoiceTotal(invoiceItems);

        // Generate shipping and create invoice item dto for shipping
        var shippingDto = OrderUtil.estimateShipping(buyingItems.stream().map(BuyingItemDto::getQuantity).reduce(0, Integer::sum));
        var shipping = orderDao.createShipping(shippingDto.getEstimatedDate());
        invoiceItems.add(OrderUtil.toInvoiceItemDtoFromShipping(shippingDto.getFee()));

        // Cost with shipping
        final Float shippingIncludedCost = originalTotal + shippingDto.getFee();

        // Apply account subscription features
        var subscription = subscriptionService.getUserSubscription(userId).getData();
        if(subscription != null && subscription.getStatus().equals(AccountSubscription.SubscriptionStatus.ACTIVE)){
            // Free shipping feature
            var freeShippingInvoiceItemDto = OrderUtil.createInvoiceItemForFreeShippingFeature(originalTotal, shippingDto.getFee(), subscription);
            if(freeShippingInvoiceItemDto != null) {
                invoiceItems.add(freeShippingInvoiceItemDto);
                recordedLog = recordedLog + "\n" + OrderUtil.freeShippingRecordLog(shippingDto.getFee(), subscription);
            }

            // Discount feature
            var discountInvoiceItemDto = OrderUtil.createInvoiceItemForDiscountFeature(originalTotal, subscription);
            if(discountInvoiceItemDto != null) {
                invoiceItems.add(discountInvoiceItemDto);
                recordedLog = recordedLog + "\n" + OrderUtil.discountRecordLog(originalTotal, subscription);
            }
        }

        // Final cost
        final Float finalTotal = OrderUtil.getInvoiceTotal(invoiceItems);

        // Compute savings
        final float saving = shippingIncludedCost - finalTotal;
        if(saving > 0) {
            userService.updateTotalSavings(userId, saving);
            recordedLog = recordedLog + "\n" + OrderUtil.savingRecordLog(saving, subscription, false);
        }

        // Check user e-cash
        final Float cash = userService.getECash(userId).getData();
        if(cash > 0){
            var eCashInvoiceItemDto = OrderUtil.createInvoiceItemForCash(cash, finalTotal);
            if(eCashInvoiceItemDto != null) {
                invoiceItems.add(eCashInvoiceItemDto);
                userService.updateECash(userId, eCashInvoiceItemDto.getSubtotal());
                recordedLog = recordedLog + "\n" + OrderUtil.eCashUsedRecordLog(-eCashInvoiceItemDto.getSubtotal(), finalTotal);
            }
        }

        // Due cost (have to pay, after using e-cash)
        final Float dueAmount = originalTotal - finalTotal;

        // Create invoice items in the database
        for(var item : invoiceItems){
            orderDao.createInvoiceItem(invoice.getId(), item.getDetail(), item.getSubtotal());
        }

        // Check referrals
        recordedLog = processReferrals(userId, buyingItems, recordedLog);

        // Create order and order items in the database
        final float total = OrderUtil.getInvoiceDueTotal(invoiceItems);
        final float wholesales = (float) OrderUtil.getWholesales(buyingItems);
        Order order = orderDao.createOrder(
                userId, invoice.getId(), shipping.getId(), recordedLog, total, total - wholesales
        );
        for(var item : buyingItems){
            orderDao.createOrderItem(order.getId(), item.getProductId(), item.getQuantity(), item.getRetailPrice(), item.getWholesalePrice());
        }

        // Remove cart items if placeOrderFromCart, or insert TemplateToOrder if placeOrderFromTemplate
        if(lastProcess != null) lastProcess.accept(order.getId());

        return ResponseDto.get(OrderUtil.toOrderDetailDto(order, invoiceItems));
    }

    private String processReferrals(Long buyerId, List<BuyingItemDto> buyingItems, String recordedLog){
        for(var item : buyingItems){
            var referral = userService.findReferralByProduct(buyerId, item.getProductId());
            if(referral == null) continue;
            var sender = referral.getSender();
            float factor = 0.05f;  // 5%
            float rewardCash = (float) (factor * item.getQuantity() * item.getRetailPrice());
            rewardCash = Float.parseFloat(String.format("%.2f", rewardCash));
            userService.updateECash(sender.getId(), rewardCash);
            userService.updateProductReferral(buyerId, item.getProductId(), rewardCash);
            recordedLog = recordedLog + "\n" + String.format("%s earned $%.2f E-Cash for his referral to '%s'", sender.getUsername(),
                    rewardCash, productDao.findProductById(item.getProductId()).getName());
        }

        return recordedLog;
    }
}
