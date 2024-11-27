package com.beaconfire.shoppingapp.entities.account.subscription;

import com.beaconfire.shoppingapp.entities.account.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class AccountSubscription {
    @Id
    @Column(name = "user_id")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Long userId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore @ToString.Exclude
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "plan", referencedColumnName = "plan")
    private SubscriptionPlan plan;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate = LocalDateTime.now();

    @Transient
    private LocalDateTime endDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate = LocalDateTime.now();

    public static enum SubscriptionStatus{
        ACTIVE, PAID_DUE, PAUSING, CANCEL
    }
    @Column(nullable = false) @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    public void updateEndDate(){
        if(getPlan().getPeriodType().equals(SubscriptionPlan.PeriodType.MONTHLY))
            setEndDate(getStartDate().plusMonths(1));
        else setEndDate(getStartDate().plusYears(1));
    }
}
