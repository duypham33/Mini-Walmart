package com.beaconfire.shoppingapp.controllers.order;

import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.order.OrderDetailDto;
import com.beaconfire.shoppingapp.dtos.order.OrderDto;
import com.beaconfire.shoppingapp.services.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/orders")
public class AdminOrderController {
    private final OrderService orderService;

    public AdminOrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<OrderDetailDto>>> getOrders(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "ALL") String status
    ){
        return orderService.searchOrders(page, pageSize, status).toResponseEntity();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseDto<OrderDetailDto>> getOrderById(@PathVariable Long orderId){
        return orderService.getOrderDetails(orderId, true).toResponseEntity();
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ResponseDto<OrderDetailDto>> cancelOrder(
            @PathVariable Long orderId, @RequestParam(required = false) Boolean returnByECash
    ){
        return orderService.cancelOrder(orderId, returnByECash, true).toResponseEntity();
    }

    @PatchMapping("/{orderId}/complete")
    public ResponseEntity<ResponseDto<OrderDetailDto>> completeOrder(
            @PathVariable Long orderId
    ){
        return orderService.completeOrder(orderId).toResponseEntity();
    }
}
