package com.beaconfire.shoppingapp.controllers.order;

import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.account.user.UserDto;
import com.beaconfire.shoppingapp.dtos.order.OrderDetailDto;
import com.beaconfire.shoppingapp.dtos.order.OrderDto;
import com.beaconfire.shoppingapp.dtos.order.OrderPreviewDto;
import com.beaconfire.shoppingapp.services.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/preview")
    public ResponseEntity<ResponseDto<OrderPreviewDto>> previewCheckout(Authentication authentication){
        var user = (UserDto) authentication.getDetails();
        return orderService.previewOrderFromCart(user.getId()).toResponseEntity();
    }

    @GetMapping("/order-template/{templateId}/preview-checkout")
    public ResponseEntity<ResponseDto<OrderPreviewDto>> previewOrderFromOrderTemplate(
            Authentication authentication, @PathVariable Long templateId
    ){
        var user = (UserDto) authentication.getDetails();
        return orderService.previewOrderFromOrderTemplate(user.getId(), templateId).toResponseEntity();
    }

    @PostMapping
    public ResponseEntity<ResponseDto<OrderDetailDto>> placeOrderFromCart(Authentication authentication){
        var user = (UserDto) authentication.getDetails();
        return orderService.placeOrderFromCart(user.getId()).toResponseEntity();
    }

    @PostMapping("/order-template/{templateId}/checkout")
    public ResponseEntity<ResponseDto<OrderDetailDto>> placeOrderFromOrderTemplate(Authentication authentication, @PathVariable Long templateId){
        var user = (UserDto) authentication.getDetails();
        return orderService.placeOrderFromOrderTemplate(user.getId(), templateId).toResponseEntity();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseDto<OrderDetailDto>> getOrderById(@PathVariable Long orderId, Authentication authentication){
        return orderService.getOrderDetails(orderId, false).toResponseEntity();
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<OrderDetailDto>>> getUserOrders(
            Authentication authentication,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "ALL") String status
    ){
        var user = (UserDto) authentication.getDetails();
        return orderService.getOrdersByUserId(user.getId(), page, pageSize, status).toResponseEntity();
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ResponseDto<OrderDetailDto>> cancelOrder(
            @PathVariable Long orderId, @RequestParam(required = false) Boolean returnByECash
    ){
        return orderService.cancelOrder(orderId, returnByECash, false).toResponseEntity();
    }
}
