package com.beaconfire.shoppingapp.dtos.account.subscription;

import com.beaconfire.shoppingapp.entities.account.subscription.SubscriptionPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SubcribeRequestDto {
    private SubscriptionPlan.PlanType planType;
}
