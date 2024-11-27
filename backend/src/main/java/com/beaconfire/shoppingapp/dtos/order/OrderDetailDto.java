package com.beaconfire.shoppingapp.dtos.order;

import com.beaconfire.shoppingapp.dtos.account.user.UserDto;
import com.beaconfire.shoppingapp.entities.order.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailDto {
    private Long id;
    private UUID orderNumber;
    private LocalDateTime placedDate;
    private UserDto orderingUser; // Admin view only
    private Order.Status status;
    private Float total;
    private String savings;
    private ShippingDto shipping;
    private List<InvoiceItemDto> invoice;
    private List<String> recordedLog;
    private List<OrderItemDto> items;
}
