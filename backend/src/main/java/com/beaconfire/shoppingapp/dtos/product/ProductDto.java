package com.beaconfire.shoppingapp.dtos.product;

import com.beaconfire.shoppingapp.entities.product.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private Long id;
    private String name;
    private Integer quantity = 1;  // Admin view only
    private Double retailPrice;
    private Double wholesalePrice;  // Admin view only
    private String imgPath;
    private Float rating;

    public static ProductDto fromProduct(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .retailPrice(product.getRetailPrice())
                .imgPath(product.getImgPath())
                .rating(product.getRating())
                .build();
    }

    public static ProductDto basicInfo(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .retailPrice(product.getRetailPrice())
                .build();
    }

    public static ProductDto fullInfo(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .retailPrice(product.getRetailPrice())
                .wholesalePrice(product.getWholesalePrice())
                .imgPath(product.getImgPath())
                .rating(product.getRating())
                .build();
    }
}
