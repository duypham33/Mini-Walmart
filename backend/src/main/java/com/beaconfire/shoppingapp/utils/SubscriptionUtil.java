package com.beaconfire.shoppingapp.utils;

import com.beaconfire.shoppingapp.entities.account.subscription.AccountSubscription;
import com.beaconfire.shoppingapp.entities.account.subscription.SubscriptionPlan;

import java.time.LocalDateTime;

public class SubscriptionUtil {
    public static boolean validateSubscription(AccountSubscription subscription){
        LocalDateTime dueDate = null;
        if(subscription.getPlan().getPeriodType().equals(SubscriptionPlan.PeriodType.MONTHLY))
            dueDate = subscription.getStartDate().plusMonths(1);
        else dueDate = subscription.getStartDate().plusYears(1);
        return dueDate.isAfter(LocalDateTime.now());
    }
}
