package com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateOrderTemplateRequestDto {
    private Long templateId;
    private Long productId;
    private Integer quantity;
    private Long substitutionPreferenceId;
}
