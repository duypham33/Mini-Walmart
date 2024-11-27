package com.beaconfire.shoppingapp.entities.order;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Shipping {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "track_code", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID trackCode;

    public static enum Status{
        PREPARING, SHIPPED, INTRANSIT, DELIVERED
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    public void prePersist() {
        if (trackCode == null) trackCode = UUID.randomUUID(); // Generate a UUID if not already set
        if(status == null) status = Status.PREPARING;
    }

    @Column(name = "estimated_date")
    private LocalDateTime estimatedDate;
}
