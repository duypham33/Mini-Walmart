package com.beaconfire.shoppingapp.dtos.account.feature;

import com.beaconfire.shoppingapp.dtos.product.ProductDto;
import com.beaconfire.shoppingapp.entities.account.feature.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CartItemDto {
    private ProductDto product;
    private Integer quantity;

    public static CartItemDto fromCartItem(CartItem cartItem){
        return CartItemDto.builder()
                .product(ProductDto.fromProduct(cartItem.getProduct()))
                .quantity(cartItem.getQuantity())
                .build();
    }
}
