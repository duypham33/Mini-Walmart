package com.beaconfire.shoppingapp.dtos.account.feature;
import com.beaconfire.shoppingapp.entities.account.feature.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CartDto {
    private Double totalPrice;
    private List<CartItemDto> items;

    public static CartDto fromItemDtos(List<CartItemDto> items){
        double total = 0d;
        for(var item : items) total += item.getQuantity() * item.getProduct().getRetailPrice();
        return CartDto.builder()
                .totalPrice(total)
                .items(items)
                .build();
    }

    public static CartDto fromItems(List<CartItem> items){
        return fromItemDtos(items.stream().map(CartItemDto::fromCartItem).toList());
    }
}
