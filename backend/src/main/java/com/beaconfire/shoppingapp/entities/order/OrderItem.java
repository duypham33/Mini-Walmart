package com.beaconfire.shoppingapp.entities.order;

import com.beaconfire.shoppingapp.entities.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore @ToString.Exclude
    private Order shoppingOder;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonIgnore @ToString.Exclude
    private Product product;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(name = "purchased_price", nullable = false)
    private Double purchasedPrice;

    @Column(name = "wholesale_price", nullable = false)
    private Double wholesalePrice;

    @Column(name = "is_completed")
    private Boolean isCompleted;    // to speed up admin statistics, without joining with ShoppingOrder table to check status

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
