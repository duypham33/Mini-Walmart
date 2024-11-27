package com.beaconfire.shoppingapp.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    private String description;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(name = "retail_price", nullable = false)
    private Double retailPrice;

    @Column(name = "wholesale_price", nullable = false)
    private Double wholesalePrice;

    @Column(name = "img_path")
    private String imgPath;

    private Float rating;
    @Column(name = "num_ratings")
    private Integer numRatings = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonIgnore @ToString.Exclude
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @JsonIgnore @ToString.Exclude
    private Brand brand;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
