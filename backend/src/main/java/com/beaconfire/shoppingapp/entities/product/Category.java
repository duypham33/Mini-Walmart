package com.beaconfire.shoppingapp.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "category_lookup", length = 200)
    private String categoryLookup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    @JsonIgnore @ToString.Exclude
    private Category parentCategory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Category_Brand",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id")
    )
    @JsonIgnore @ToString.Exclude
    private Set<Brand> brands = new HashSet<>();
}
