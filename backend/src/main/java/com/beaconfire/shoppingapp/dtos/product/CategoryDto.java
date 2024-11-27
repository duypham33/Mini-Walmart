package com.beaconfire.shoppingapp.dtos.product;

import com.beaconfire.shoppingapp.entities.product.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString @JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    private Long id;
    private String name;
    private String categoryPath;

    public static CategoryDto fromCategory(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .categoryPath(category.getCategoryLookup())
                .build();
    }

    public static CategoryDto getWithNameFromCategory(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
