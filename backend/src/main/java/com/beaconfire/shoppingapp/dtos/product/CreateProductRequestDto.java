package com.beaconfire.shoppingapp.dtos.product;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class CreateProductRequestDto {
    private String name;
    private String description;
    private Integer quantity;
    private Double retailPrice;
    private Double wholesalePrice;
    private Long categoryId;
    private Long brandId;
}
