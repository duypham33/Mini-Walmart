package com.beaconfire.shoppingapp.daos.account;

import com.beaconfire.shoppingapp.daos.AbstractHibernateDao;
import com.beaconfire.shoppingapp.entities.account.subscription.AccountSubscription;
import com.beaconfire.shoppingapp.entities.account.subscription.SubscriptionPlan;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriptionDao extends AbstractHibernateDao {
    public List<SubscriptionPlan> getAll(){
        return this.getAll(SubscriptionPlan.class);
    }

    public AccountSubscription findUserSubscription(Long userId){
        return findById(userId, AccountSubscription.class);
    }

    public AccountSubscription createUserSubscription(Long userId, SubscriptionPlan.PlanType planType, AccountSubscription.SubscriptionStatus status){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = "insert into AccountSubscription (userId, status, plan) select :userId, :status, p from SubscriptionPlan p where p.plan = :planType";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("status", status);
            query.setParameter("planType", planType);
            query.executeUpdate();
            tx.commit();
            return findById(userId, AccountSubscription.class);
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public void deleteUserSubscription(AccountSubscription subscription){
        delete(subscription);
    }

    public AccountSubscription updateUserSubscription(Long userId, AccountSubscription.SubscriptionStatus status){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = "update AccountSubscription set status = :status, updatedDate = now() where userId = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("status", status);
            int count = query.executeUpdate();
            tx.commit();
            return count > 0 ? findById(userId, AccountSubscription.class) : null;
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }

        return null;
    }
}
