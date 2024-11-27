package com.beaconfire.shoppingapp.entities.account.user;

import com.beaconfire.shoppingapp.entities.account.feature.CartItem;
import com.beaconfire.shoppingapp.entities.account.feature.ECash;
import com.beaconfire.shoppingapp.entities.account.feature.TotalSavings;
import com.beaconfire.shoppingapp.entities.account.subscription.AccountSubscription;
import com.beaconfire.shoppingapp.entities.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "hash_password", nullable = false, length = 125)
    private String hashPassword;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role", referencedColumnName = "role")
    private UserRole role;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private AccountSubscription subscription;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "WatchList",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    @JsonIgnore
    @ToString.Exclude
    private Set<Product> watchList = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private ECash eCash;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private TotalSavings totalSavings;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
