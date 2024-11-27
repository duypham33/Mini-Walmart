package com.beaconfire.shoppingapp.entities.order;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class Invoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID code;

    public static enum Status{
        PENDING, PAID
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    public void prePersist() {
        if(code == null) code = UUID.randomUUID(); // Generate a UUID if not already set
        if(status == null) status = Status.PENDING;
        if(createdDate == null) createdDate = LocalDateTime.now();
    }

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice", orphanRemoval = true)
    private List<InvoiceItem> items = new ArrayList<>();  // reserved insertion order
}
