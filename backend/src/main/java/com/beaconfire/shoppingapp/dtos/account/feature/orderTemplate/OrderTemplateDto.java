package com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate;
import com.beaconfire.shoppingapp.entities.account.feature.orderTemplate.OrderTemplate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderTemplateDto {
    private Long id;
    private String name;
    private List<OrderTemplateItemDto> items;

    public static OrderTemplateDto fromOrderTemplate(OrderTemplate orderTemplate){
        return OrderTemplateDto.builder()
                .id(orderTemplate.getId())
                .name(orderTemplate.getName())
                .items(orderTemplate.getItems().stream()
                        .map(i -> {
                            var n = OrderTemplateItemDto.fromOrderTemplateItem(i);
                            n.setTemplateName(null);
                            return n;
                        })
                        .toList())
                .build();
    }
}
