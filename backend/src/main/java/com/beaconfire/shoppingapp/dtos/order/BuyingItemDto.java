package com.beaconfire.shoppingapp.dtos.order;

import com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate.OrderTemplateItemDto;
import com.beaconfire.shoppingapp.entities.account.feature.CartItem;
import com.beaconfire.shoppingapp.entities.product.Product;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class BuyingItemDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer inStock;
    private Double retailPrice;
    private Double wholesalePrice;
    private Long sourceObjectId;

    public static enum SourceType{
        CART_ITEM, ORDER_ITEM, ORDER_TEMPLATE_ITEM
    }
    private SourceType sourceType;

    public static BuyingItemDto fromCartItem(CartItem cartItem){
        var product = cartItem.getProduct();
        return BuyingItemDto.builder()
                .productId(product.getId()).productName(product.getName())
                .retailPrice(product.getRetailPrice()).wholesalePrice(product.getWholesalePrice())
                .quantity(cartItem.getQuantity()).inStock(product.getQuantity())
                .sourceObjectId(cartItem.getId()).sourceType(SourceType.CART_ITEM)
                .build();
    }

    public static BuyingItemDto fromOrderTemplateItem(OrderTemplateItemDto item, Product product){
        return BuyingItemDto.builder()
                .productId(product.getId()).productName(product.getName())
                .retailPrice(product.getRetailPrice()).wholesalePrice(product.getWholesalePrice())
                .quantity(item.getQuantity()).inStock(product.getQuantity())
                .sourceObjectId(item.getId()).sourceType(SourceType.ORDER_TEMPLATE_ITEM)
                .build();
    }
}
