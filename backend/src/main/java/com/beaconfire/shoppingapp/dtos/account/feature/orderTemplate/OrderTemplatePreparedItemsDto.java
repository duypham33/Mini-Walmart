package com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate;

import com.beaconfire.shoppingapp.dtos.order.BuyingItemDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class OrderTemplatePreparedItemsDto {
    private List<BuyingItemDto> buyingItems = new ArrayList<>();
    private List<String> recordLog = new ArrayList<>();
}
