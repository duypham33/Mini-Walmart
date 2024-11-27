package com.beaconfire.shoppingapp.entities.account.subscription;

import lombok.*;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SubscriptionPlan {
    public static enum PlanType{
        BASIC, PREMIUM
    }

    @Id @Enumerated(EnumType.STRING)
    private PlanType plan;

    public static enum PeriodType{
        MONTHLY, YEARLY
    }

    @Column(name = "period_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PeriodType periodType;

    @Column(nullable = false)
    private Float price;

    @Column(name = "free_shipping_threshold")
    private Float freeShippingThreshold;

    @Column(name = "discount_threshold")
    private Float discountThreshold;

    @Column(name = "discount_percent")
    private Short discountPercent;

    @Override
    public String toString(){
        String res = "";
        res += plan.toString() + " plan: $" + getPrice().toString() + " " + periodType.toString() + ", features: ";
        if(freeShippingThreshold != null){
            res += ("free shipping for any order higher than $" + freeShippingThreshold);
        }
        if(discountThreshold != null){
            if(freeShippingThreshold != null) res += ",  and  ";
            res += (discountPercent + "% discount on any order higher than $" + discountThreshold);
        }
        res += "\n";
        return res;
    }
}
