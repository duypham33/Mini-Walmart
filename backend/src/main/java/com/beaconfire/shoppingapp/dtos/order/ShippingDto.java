package com.beaconfire.shoppingapp.dtos.order;

import com.beaconfire.shoppingapp.entities.order.Shipping;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class ShippingDto {
    private Long id;
    private LocalDateTime estimatedDate;
    private Shipping.Status status;
    private UUID trackCode;

    public static ShippingDto fromShipping(Shipping shipping){
        return ShippingDto.builder()
                .id(shipping.getId())
                .estimatedDate(shipping.getEstimatedDate())
                .status(shipping.getStatus())
                .trackCode(shipping.getTrackCode())
                .build();
    }
}
