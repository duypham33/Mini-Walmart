package com.beaconfire.shoppingapp.utils;

import com.beaconfire.shoppingapp.dtos.account.user.UserDto;
import com.beaconfire.shoppingapp.dtos.order.*;
import com.beaconfire.shoppingapp.entities.account.subscription.AccountSubscription;
import com.beaconfire.shoppingapp.entities.order.Order;
import com.beaconfire.shoppingapp.entities.product.Product;
import com.beaconfire.shoppingapp.exceptions.NotEnoughInventoryException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class OrderUtil {
    public static InvoiceItemDto toInvoiceItemDtoFromBuyingItem(BuyingItemDto buyingItemDto){
        return InvoiceItemDto.builder()
                .detail(String.format("%d  x ($%.2f) %s", buyingItemDto.getQuantity(), buyingItemDto.getRetailPrice(), buyingItemDto.getProductName()))
                .subtotal((float) (buyingItemDto.getQuantity() * buyingItemDto.getRetailPrice()))
                .build();
    }

    public static EstimatedShippingDto estimateShipping(int itemsTotal){
        float fee = 9;
        if(itemsTotal > 20) fee = 20;
        else if(itemsTotal > 15) fee = 15;
        else if(itemsTotal > 10) fee = 12;
        return EstimatedShippingDto.builder()
                .fee(fee).estimatedDate(LocalDateTime.now().plusDays(2))
                .build();
    }

    public static InvoiceItemDto toInvoiceItemDtoFromShipping(float fee){
        return InvoiceItemDto.builder()
                .detail(String.format("Shipping: $%.2f", fee))
                .subtotal(fee)
                .build();
    }

    public static float getInvoiceTotal(List<InvoiceItemDto> invoiceItems){
        return invoiceItems.stream().map(InvoiceItemDto::getSubtotal).reduce(0f, Float::sum);
    }

    public static double getWholesales(List<BuyingItemDto> items){
        return items.stream().map(i -> i.getQuantity() * i.getWholesalePrice()).reduce(0d, Double::sum);
    }

    public static float getInvoiceDueTotal(List<InvoiceItemDto> invoiceItems){
        return invoiceItems.stream()
                .filter(i -> !i.getDetail().startsWith("E Cash: -"))
                .map(InvoiceItemDto::getSubtotal).reduce(0f, Float::sum);
    }

//    public static float getInvoiceDueTotal(Order order){
//        List<InvoiceItemDto> invoiceItemDtos = order.getInvoice().getItems().stream().map(InvoiceItemDto::fromInvoiceItem).toList();
//        return getInvoiceDueTotal(invoiceItemDtos);
//    }

    public static InvoiceItemDto createInvoiceItemForFreeShippingFeature(
            Float originalTotal, Float shippingFee, AccountSubscription subscription
    ){
        Float t = subscription.getPlan().getFreeShippingThreshold();
        if(t == null || t > originalTotal) return null;
        return InvoiceItemDto.builder()
                .detail(String.format("Free Shipping: -$%.2f", shippingFee))
                .subtotal(-shippingFee)
                .build();
    }

    public static InvoiceItemDto createInvoiceItemForDiscountFeature(
            Float originalTotal, AccountSubscription subscription
    ){
        var plan = subscription.getPlan();
        Float t = plan.getDiscountThreshold();
        if(t == null || t > originalTotal) return null;
        float discount = Math.round(originalTotal * plan.getDiscountPercent() / 100);
        return InvoiceItemDto.builder()
                .detail(plan.getDiscountPercent() + "%" + String.format(" discount: -$%.2f", discount))
                .subtotal(-discount)
                .build();
    }

    public static InvoiceItemDto createInvoiceItemForCash(float cash, float cost){
        final float cashUsed = Math.min(cash, cost);
        return InvoiceItemDto.builder()
                .detail(String.format("E Cash: -$%.2f", cashUsed))
                .subtotal(-cashUsed)
                .build();
    }

    public static OrderPreviewDto createOrderPreview(
            EstimatedShippingDto shipping, List<InvoiceItemDto> invoiceItems, Float saving, AccountSubscription subscription, List<String> recordLog
    ){
        var builder = OrderPreviewDto.builder()
                .shipping(shipping).invoiceItems(invoiceItems)
                .finalDueAmount(getInvoiceTotal(invoiceItems));
        if(saving > 0)
            builder.saving(savingRecordLog(saving, subscription, true));
        if(!recordLog.isEmpty()) builder.recordLog(recordLog);
        return builder.build();
    }

    public static String freeShippingRecordLog(Float shippingFee, AccountSubscription subscription){
        return String.format("Applied FREE shipping of $%.2f for %s subscription", shippingFee, subscription.getPlan().getPlan());
    }

    public static String discountRecordLog(Float originalTotal, AccountSubscription subscription){
        var plan = subscription.getPlan();
        Float t = plan.getDiscountThreshold();
        if(t == null || t > originalTotal) return null;
        float discount = Math.round(originalTotal * plan.getDiscountPercent() / 100);
        return "Applied " + plan.getDiscountPercent() + "%" + String.format(" discount (= $%.2f) for %s subscription", discount, plan.getPlan());
    }

    public static String savingRecordLog(Float saving, AccountSubscription subscription, boolean isPreview){
        return (isPreview ? "You will save $" : "Saved $") + saving +
                " with your " + subscription.getPlan().getPlan() + " subscription";
    }

    public static String eCashUsedRecordLog(Float cash, Float fee){
        return String.format("$%.2f E-Cash was applied to the order fee of %.2f", cash, fee);
    }

    public static void checkStock(List<BuyingItemDto> buyingItems){
        for(var item : buyingItems){
            if(item.getQuantity() <= item.getInStock()) continue;
            throw new NotEnoughInventoryException(
                    item.getProductId(), item.getProductName(), item.getInStock(), item.getQuantity()
            );
        }
    }

    private static List<String> parseOrderLog(String recordedLog){
        return Arrays.stream(recordedLog.split("\n"))
                .filter(s -> !s.isBlank())
                .toList();
    }

    private static String getSavingsFromLog(List<String> recordedLog){
        String res = recordedLog.stream().filter(s -> s.startsWith("Saved ") && s.contains("subscription"))
                .findFirst().orElse(null);
        return res == null ? null : res.replace("Saved", "You saved");
    }

    public static OrderDetailDto toOrderDetailDto(Order order){
        List<InvoiceItemDto> invoiceItemDtos = order.getInvoice().getItems().stream().map(InvoiceItemDto::fromInvoiceItem).toList();
        return toOrderDetailDto(order, invoiceItemDtos);
    }

    public static OrderDetailDto toOrderDetailDtoForSeller(Order order){
        OrderDetailDto orderDetailDto = toOrderDetailDto(order);
        orderDetailDto.setOrderingUser(UserDto.fromUser(order.getUser()));
        orderDetailDto.setSavings(null);
        return orderDetailDto;
    }

    public static OrderDetailDto toOrderDetailDto(Order order, List<InvoiceItemDto> invoiceItemDtos){
        List<String> recordedLog = parseOrderLog(order.getRecordedLog());
        var builder = OrderDetailDto.builder()
                .id(order.getId()).orderNumber(order.getCode())
                .placedDate(order.getPlacedDate()).status(order.getStatus())
                .total(order.getTotal())
                .savings(getSavingsFromLog(recordedLog))
                .shipping(ShippingDto.fromShipping(order.getShipping()))
                .invoice(invoiceItemDtos)
                .recordedLog(recordedLog)
                .items(order.getItems().stream().map(OrderItemDto::fromOrderItem).toList());

        return builder.build();
    }

    public static String cancelRecordLog(Boolean returnByECash){
        return "Cancelled by " + (returnByECash != null && returnByECash ? "E-Cash" : "Refund");
    }

    public static BuyingItemDto buyingItemFromSubstitution(Long itemId, Product substitution, Integer quantity){
        return BuyingItemDto.builder()
                .productId(substitution.getId()).productName(substitution.getName())
                .retailPrice(substitution.getRetailPrice()).wholesalePrice(substitution.getWholesalePrice())
                .quantity(Math.min(substitution.getQuantity(), quantity)).inStock(substitution.getQuantity())
                .sourceObjectId(itemId).sourceType(BuyingItemDto.SourceType.ORDER_TEMPLATE_ITEM)
                .build();
    }

    public static String substitutionRecordLog(String product, Product substitution, Integer quantity){
        Integer used = Math.min(substitution.getQuantity(), quantity);
        if(used.equals(quantity))
            return String.format("Substituted %d  x  '%s'  by  '%s'", used, product, substitution.getName());
        return String.format("Substituted only %d  x  '%s'  by  '%s' since the substitution is also out of stock",
                used, product, substitution.getName());
    }
}
