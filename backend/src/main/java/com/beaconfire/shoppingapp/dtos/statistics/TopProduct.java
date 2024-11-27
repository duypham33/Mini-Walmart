package com.beaconfire.shoppingapp.dtos.statistics;

import com.beaconfire.shoppingapp.dtos.product.ProductDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TopProduct {
    private ProductDto product;
    private String metric;
}
