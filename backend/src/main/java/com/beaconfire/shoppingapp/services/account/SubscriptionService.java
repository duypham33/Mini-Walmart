package com.beaconfire.shoppingapp.services.account;

import com.beaconfire.shoppingapp.daos.account.SubscriptionDao;
import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.entities.account.subscription.AccountSubscription;
import com.beaconfire.shoppingapp.entities.account.subscription.SubscriptionPlan;
import com.beaconfire.shoppingapp.utils.SubscriptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
    private final SubscriptionDao subscriptionDao;

    public SubscriptionService(SubscriptionDao subscriptionDao){
        this.subscriptionDao = subscriptionDao;
    }

    public List<String> viewPlans(){
        return subscriptionDao.getAll().stream().map(SubscriptionPlan::toString).toList();
    }

    private AccountSubscription findUserSubscription(Long userId){
        AccountSubscription subscription = subscriptionDao.findUserSubscription(userId);
        // validate expiration
        if(subscription != null && !SubscriptionUtil.validateSubscription(subscription)){
            subscription = subscriptionDao.updateUserSubscription(userId, AccountSubscription.SubscriptionStatus.PAID_DUE);
        }

        return subscription;
    }

    public ResponseDto<AccountSubscription> getUserSubscription(Long userId){
        AccountSubscription subscription = findUserSubscription(userId);
        if(subscription != null) subscription.updateEndDate();
        return ResponseDto.get(subscription, subscription == null ? "You have no subscription!" : null, HttpStatus.OK);
    }

    public ResponseDto<AccountSubscription> subscribe(Long userId, SubscriptionPlan.PlanType planType){
        AccountSubscription subscription = findUserSubscription(userId);
        if(subscription != null && subscription.getStatus().equals(AccountSubscription.SubscriptionStatus.ACTIVE)){
            return ResponseDto.get(null, "You are having an active subscription! So you cannot subscribe now!");
        }

        if(subscription != null){
            subscriptionDao.deleteUserSubscription(subscription);
        }

        // Create new
        var newSub = subscriptionDao.createUserSubscription(userId, planType,
                AccountSubscription.SubscriptionStatus.ACTIVE);
        newSub.updateEndDate();
        return ResponseDto.get(newSub);
    }

    public ResponseDto<AccountSubscription> cancelUserSubscription(Long userId){
        AccountSubscription subscription = subscriptionDao.updateUserSubscription(userId, AccountSubscription.SubscriptionStatus.CANCEL);
        return ResponseDto.get(subscription, subscription == null ? "You have no subscription!" : null);
    }
}
