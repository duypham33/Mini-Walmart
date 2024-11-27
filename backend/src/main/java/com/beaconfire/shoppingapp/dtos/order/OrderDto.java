package com.beaconfire.shoppingapp.dtos.order;

import com.beaconfire.shoppingapp.entities.order.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private Long id;
    private UUID orderNumber;
    private LocalDateTime placedDate;
    private String orderingUser;  // Admin view only
    private Order.Status status;

    public static OrderDto fromOrder(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .orderNumber(order.getCode())
                .placedDate(order.getPlacedDate())
                .status(order.getStatus())
                .build();
    }

    public static OrderDto fullInfo(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .orderNumber(order.getCode())
                .placedDate(order.getPlacedDate())
                .status(order.getStatus())
                .orderingUser(order.getUser().getUsername())
                .build();
    }
}
