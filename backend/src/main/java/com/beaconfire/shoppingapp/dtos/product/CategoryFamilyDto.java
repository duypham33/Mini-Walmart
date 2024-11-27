package com.beaconfire.shoppingapp.dtos.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryFamilyDto {
    private Long id;
    private String name;
    List<CategoryDto> children;
}
