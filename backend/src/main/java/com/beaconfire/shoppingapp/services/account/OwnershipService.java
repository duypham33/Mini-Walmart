package com.beaconfire.shoppingapp.services.account;

import com.beaconfire.shoppingapp.daos.account.OwnershipDao;
import org.springframework.stereotype.Service;

@Service
public class OwnershipService {
    private final OwnershipDao ownershipDao;

    public OwnershipService(OwnershipDao ownershipDao){
        this.ownershipDao = ownershipDao;
    }

    public Boolean checkOrderTemplateOwnership(Long userId, Long templateId){
        return ownershipDao.checkOrderTemplateOwnership(userId, templateId);
    }

    public Boolean checkOrderOwnership(Long userId, Long orderId){
        return ownershipDao.checkOrderOwnership(userId, orderId);
    }

    public Boolean checkWatchListOwnership(Long userId, Long watchListProductId){
        return ownershipDao.checkWatchListOwnership(userId, watchListProductId);
    }
}
