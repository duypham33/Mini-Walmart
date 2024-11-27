package com.beaconfire.shoppingapp.entities.account.watchlist;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString @IdClass(WatchListId.class)
public class WatchList {
    @Id @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "recent_watch_date", nullable = false)
    private LocalDateTime recentWatchDate = LocalDateTime.now();
}
