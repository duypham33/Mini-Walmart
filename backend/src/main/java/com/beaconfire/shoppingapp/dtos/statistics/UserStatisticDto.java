package com.beaconfire.shoppingapp.dtos.statistics;

import com.beaconfire.shoppingapp.dtos.product.ProductDto;
import lombok.*;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class UserStatisticDto {
    private String subscription;
    private String savings;
    private String eCash;
    private String completedOrders;
    private String cancelOrders;
    private List<TopProduct> frequentProducts;
    private List<TopProduct> recentProducts;
}
