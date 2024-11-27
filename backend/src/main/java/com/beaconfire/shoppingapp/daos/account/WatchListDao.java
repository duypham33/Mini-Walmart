package com.beaconfire.shoppingapp.daos.account;

import com.beaconfire.shoppingapp.daos.AbstractHibernateDao;
import com.beaconfire.shoppingapp.entities.account.watchlist.WatchList;
import com.beaconfire.shoppingapp.entities.account.watchlist.WatchListId;
import com.beaconfire.shoppingapp.entities.product.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class WatchListDao extends AbstractHibernateDao {
    public WatchList findWatchListByIds(Long userId, Long productId){
        return findById(WatchListId.builder().userId(userId).productId(productId).build(), WatchList.class);
    }

    public Product getWatchProduct(Long productId){
        return findById(productId, Product.class);
    }

    public List<Product> getWatchListForUser(Long userId){
        var session = getCurrentSession();
        final String hql = """
                        select p from WatchList w join Product p on w.productId = p.id 
                        where w.userId = :userId and p.quantity > 0 order by w.recentWatchDate desc
                        """;
        var query = session.createQuery(hql, Product.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public boolean addProduct(Long userId, Long productId){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
//            final String hql = """
//                insert into WatchList (userId, productId) select :userId, :productId
//            """;
//            Query query = session.createQuery(hql);
//            query.setParameter("userId", userId);
//            query.setParameter("productId", productId);
//            query.executeUpdate();

            session.save(WatchList.builder()
                    .userId(userId).productId(productId)
                    .recentWatchDate(LocalDateTime.now())
                    .build());
            tx.commit();
            return true;
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public void removeProduct(Long userId, Long productId){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                delete from WatchList where userId = :userId and productId = :productId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("productId", productId);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
