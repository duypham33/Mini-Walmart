package com.beaconfire.shoppingapp.entities.account.feature;

import com.beaconfire.shoppingapp.entities.account.user.User;
import com.beaconfire.shoppingapp.entities.product.Product;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductReferral {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "referred_id", referencedColumnName = "id")
    private User referredUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "purchased_date")
    private LocalDateTime purchasedDate;

    @Column(name = "reward_cash")
    private Float rewardCash;
}
