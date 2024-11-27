package com.beaconfire.shoppingapp.dtos.product;

import com.beaconfire.shoppingapp.entities.product.Brand;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class BrandDto {
    private Long id;
    private String name;

    public static BrandDto fromBrand(Brand brand){
        return BrandDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
