package com.beaconfire.shoppingapp.daos.account;

import com.beaconfire.shoppingapp.daos.AbstractHibernateDao;
import com.beaconfire.shoppingapp.entities.account.feature.orderTemplate.OrderTemplate;
import com.beaconfire.shoppingapp.entities.account.watchlist.WatchList;
import com.beaconfire.shoppingapp.entities.order.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OwnershipDao extends AbstractHibernateDao {
//    @Transactional
    public Boolean checkOrderTemplateOwnership(Long userId, Long templateId){
        try {
            var session = getCurrentSession();
            final String hql = "from OrderTemplate where id = :templateId and user.id = :userId";
            var query = session.createQuery(hql, OrderTemplate.class);
            query.setParameter("userId", userId);
            query.setParameter("templateId", templateId);
            return query.getSingleResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    @Transactional
    public Boolean checkOrderOwnership(Long userId, Long orderId){
        try {
            var session = getCurrentSession();
            final String hql = "from Order where id = :orderId and user.id = :userId";
            var query = session.createQuery(hql, Order.class);
            query.setParameter("userId", userId);
            query.setParameter("orderId", orderId);
            return query.getSingleResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    @Transactional
    public Boolean checkWatchListOwnership(Long userId, Long watchListProductId){
        try {
            var session = getCurrentSession();
            final String hql = "from WatchList where userId = :userId and productId = :watchListProductId";
            var query = session.createQuery(hql, WatchList.class);
            query.setParameter("userId", userId);
            query.setParameter("watchListProductId", watchListProductId);
            return query.getSingleResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
