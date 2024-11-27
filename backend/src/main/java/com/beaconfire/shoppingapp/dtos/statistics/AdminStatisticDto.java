package com.beaconfire.shoppingapp.dtos.statistics;

import lombok.*;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class AdminStatisticDto {
    private String userTotal;
    private String subscriptions;
    private String orderTotal;
    private String subscriptionProfit;
    private String sellingProfit;
    private String categoryTotal;
    private String brandTotal;
    private String productTotal;
    private String soldProductTotal;
    private List<TopProduct> profitProducts;
    private List<TopProduct> popularProducts;
}
