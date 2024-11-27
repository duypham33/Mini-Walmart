package com.beaconfire.shoppingapp.entities.account.feature.orderTemplate;

import com.beaconfire.shoppingapp.entities.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderTemplateItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", referencedColumnName = "id")
    @JsonIgnore @ToString.Exclude
    private OrderTemplate orderTemplate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonIgnore @ToString.Exclude
    private Product product;

    @Column(nullable = false)
    private Integer quantity = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "substitution_preference_id", referencedColumnName = "id")
    @JsonIgnore @ToString.Exclude
    private Product substitutionPreference;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTemplateItem that = (OrderTemplateItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
