package com.beaconfire.shoppingapp.entities.order;

import com.beaconfire.shoppingapp.entities.account.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString @Table(name = "ShoppingOrder")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID code;

    public static enum Status{
        PREPARING, SHIPPED, COMPLETED, CANCEL
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    public void prePersist() {
        if(code == null) code = UUID.randomUUID(); // Generate a UUID if not already set
        if(status == null) status = Status.PREPARING;
        if(placedDate == null) placedDate = LocalDateTime.now();
    }

    @Column(name = "placed_date", nullable = false)
    private LocalDateTime placedDate = LocalDateTime.now();

    @Column(name = "recorded_log")
    private String recordedLog;

    @Column(nullable = false)
    private Float total;  // to speed up admin statistics

    @Column(nullable = false)
    private Float profit;   // to speed up admin statistics

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipping_id", referencedColumnName = "id")
    private Shipping shipping;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingOder", orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();
}
