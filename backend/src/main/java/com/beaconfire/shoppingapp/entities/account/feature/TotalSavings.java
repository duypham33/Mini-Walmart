package com.beaconfire.shoppingapp.entities.account.feature;
import com.beaconfire.shoppingapp.entities.account.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class TotalSavings {
    @Id
    @Column(name = "user_id")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Long userId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore @ToString.Exclude
    private User user;

    @Column(nullable = false)
    private Float amount = 0f;
}
