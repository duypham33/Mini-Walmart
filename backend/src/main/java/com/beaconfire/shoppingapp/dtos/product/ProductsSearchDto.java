package com.beaconfire.shoppingapp.dtos.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductsSearchDto {
    private CategoryDto category;
    private Set<BrandDto> brands;
    private List<ProductDto> products;
}
