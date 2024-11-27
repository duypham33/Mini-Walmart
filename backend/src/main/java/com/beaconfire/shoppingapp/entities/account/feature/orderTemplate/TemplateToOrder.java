package com.beaconfire.shoppingapp.entities.account.feature.orderTemplate;

import com.beaconfire.shoppingapp.entities.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString @IdClass(TemplateToOrderId.class)
public class TemplateToOrder {
    @Id @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Id @Column(name = "template_id", nullable = false)
    private Long templateId;

//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id", referencedColumnName = "id")
//    @JsonIgnore
//    @ToString.Exclude
//    private Order order;
//
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "template_id", referencedColumnName = "id")
//    @JsonIgnore @ToString.Exclude
//    private OrderTemplate orderTemplate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
