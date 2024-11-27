package com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate;
import com.beaconfire.shoppingapp.dtos.product.ProductDto;
import com.beaconfire.shoppingapp.entities.account.feature.orderTemplate.OrderTemplateItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderTemplateItemDto {
    private Long id;
    private String templateName;
    private ProductDto product;
    private Integer quantity;
    private ProductDto substitutionPreference;
    private String substitution;

    public static OrderTemplateItemDto fromOrderTemplateItem(OrderTemplateItem item){
        var substitutionPreference = item.getSubstitutionPreference();
        return OrderTemplateItemDto.builder()
                .id(item.getId()).templateName(item.getOrderTemplate().getName())
                .product(ProductDto.basicInfo(item.getProduct()))
                .quantity(item.getQuantity())
                .substitutionPreference(substitutionPreference == null ? null : ProductDto.basicInfo(substitutionPreference))
                .substitution(substitutionPreference == null ? "Best Match (Relevant + Price)" : null)
                .build();
    }
}
