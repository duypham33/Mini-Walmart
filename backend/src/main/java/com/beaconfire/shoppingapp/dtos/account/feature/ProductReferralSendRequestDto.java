package com.beaconfire.shoppingapp.dtos.account.feature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductReferralSendRequestDto {
    private Long referredId;
    private Long productId;
}
