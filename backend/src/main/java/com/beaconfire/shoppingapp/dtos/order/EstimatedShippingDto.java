package com.beaconfire.shoppingapp.dtos.order;

import lombok.*;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class EstimatedShippingDto {
    private LocalDateTime estimatedDate;
    private Float fee;
}
