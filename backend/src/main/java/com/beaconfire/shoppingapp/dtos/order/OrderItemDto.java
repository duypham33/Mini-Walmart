package com.beaconfire.shoppingapp.dtos.order;

import com.beaconfire.shoppingapp.entities.order.OrderItem;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class OrderItemDto {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private String productImgPath;
    private Double purchasedPrice;
    private Integer quantity;

    public static OrderItemDto fromOrderItem(OrderItem item){
        var product = item.getProduct();
        return OrderItemDto.builder()
                .orderItemId(item.getId())
                .productId(product.getId())
                .productName(product.getName())
                .productImgPath(product.getImgPath())
                .purchasedPrice(item.getPurchasedPrice())
                .quantity(item.getQuantity())
                .build();
    }
}
