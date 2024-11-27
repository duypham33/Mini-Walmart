package com.beaconfire.shoppingapp.dtos.product;

import com.beaconfire.shoppingapp.entities.product.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailDto {
    private CategoryDto category;
    private BrandDto brand;
    private Long id;
    private String name;
    private String description;
    private Integer quantity = 1;   // Admin view only
    private Double retailPrice;
    private Double wholesalePrice;  // Admin view only
    private String imgPath;
    private Float rating;

    public static ProductDetailDto fromProduct(Product product){
        return ProductDetailDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .retailPrice(product.getRetailPrice())
                .imgPath(product.getImgPath())
                .rating(product.getRating())
                .category(CategoryDto.fromCategory(product.getCategory()))
                .brand(BrandDto.fromBrand(product.getBrand()))
                .build();
    }

    public static ProductDetailDto fullInfo(Product product){
        return ProductDetailDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .retailPrice(product.getRetailPrice())
                .wholesalePrice(product.getWholesalePrice())
                .imgPath(product.getImgPath())
                .rating(product.getRating())
                .category(CategoryDto.fromCategory(product.getCategory()))
                .brand(BrandDto.fromBrand(product.getBrand()))
                .build();
    }
}
