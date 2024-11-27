package com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateOrderTemplateRequestDto {
    private String name;
}
