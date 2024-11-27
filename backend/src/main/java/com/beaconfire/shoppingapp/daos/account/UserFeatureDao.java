package com.beaconfire.shoppingapp.daos.account;

import com.beaconfire.shoppingapp.daos.AbstractHibernateDao;
import com.beaconfire.shoppingapp.entities.account.feature.CartItem;
import com.beaconfire.shoppingapp.entities.account.feature.ECash;
import com.beaconfire.shoppingapp.entities.account.feature.ProductReferral;
import com.beaconfire.shoppingapp.entities.account.feature.TotalSavings;
import com.beaconfire.shoppingapp.entities.account.feature.orderTemplate.OrderTemplate;
import com.beaconfire.shoppingapp.entities.account.feature.orderTemplate.OrderTemplateItem;
import com.beaconfire.shoppingapp.entities.account.feature.orderTemplate.TemplateToOrder;
import com.beaconfire.shoppingapp.entities.account.user.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserFeatureDao extends AbstractHibernateDao {
    public CartItem findCartItem(long userId, long productId){
        try {
            var session = getCurrentSession();
            final String hql = "from CartItem c where c.user.id = :userId and product.id = :productId";
            var query = session.createQuery(hql, CartItem.class);
            query.setParameter("userId", userId);
            query.setParameter("productId", productId);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CartItem> findCartItems(long userId){
        try {
            var session = getCurrentSession();
            final String hql = "from CartItem c where c.user.id = :userId";
            var query = session.createQuery(hql, CartItem.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public CartItem createCartItem(long userId, long productId, int quantity){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                insert into CartItem (user, product, quantity) select u, p, :quantity from User u, Product p
                where u.id = :userId and p.id = :productId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("productId", productId);
            query.setParameter("quantity", quantity);
            query.executeUpdate();
            tx.commit();
            return findCartItem(userId, productId);
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public void updateCartItem(long id, int quantity){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                update CartItem set quantity = :quantity where id = :id
            """;
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            query.setParameter("quantity", quantity);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deleteCartItem(Long id){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                delete CartItem where id = :id
            """;
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void clearCart(Long userId){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                delete CartItem where user.id = :userId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void createECash(Long userId, Float amount){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
//            final String hql = """
//                insert into ECash (userId, amount) select :userId, :amount
//            """;
//            Query query = session.createQuery(hql);
//            query.setParameter("userId", userId);
//            query.setParameter("amount", amount);
//            query.executeUpdate();
            User user = session.get(User.class, userId);
            ECash cash = ECash.builder().userId(userId).amount(amount).build();
            cash.setUser(user);
            user.setECash(cash);
            session.saveOrUpdate(cash);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void updateECash(Long userId, Float amount){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                update ECash set amount = :amount where userId = :userId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("amount", amount);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void createSavings(Long userId, Float amount){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            User user = session.get(User.class, userId);
            TotalSavings totalSavings = TotalSavings.builder().userId(userId).amount(amount).build();
            totalSavings.setUser(user);
            user.setTotalSavings(totalSavings);
            session.saveOrUpdate(totalSavings);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void updateTotalSavings(Long userId, Float amount){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                update TotalSavings set amount = :amount where userId = :userId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("amount", amount);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public OrderTemplate createOrderTemplate(Long userId, String templateName){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            User user = session.get(User.class, userId);
            OrderTemplate orderTemplate = OrderTemplate.builder().user(user)
                    .name(templateName).createdDate(LocalDateTime.now()).build();
            session.save(orderTemplate);
            tx.commit();
            return orderTemplate;
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public OrderTemplate findOrderTemplateById(Long templateId){
        return findById(templateId, OrderTemplate.class);
    }

    public List<OrderTemplate> findOrderTemplates(Long userId){
        try {
            var session = getCurrentSession();
            final String hql = "from OrderTemplate t where t.user.id = :userId";
            var query = session.createQuery(hql, OrderTemplate.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public OrderTemplateItem findOrderTemplateItem(Long templateId, Long productId){
        try {
            var session = getCurrentSession();
            final String hql = "from OrderTemplateItem i where i.orderTemplate.id = :templateId and i.product.id = :productId";
            var query = session.createQuery(hql, OrderTemplateItem.class);
            query.setParameter("templateId", templateId);
            query.setParameter("productId", productId);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteOrderTemplateItem(Long id){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                delete OrderTemplateItem where id = :id
            """;
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public OrderTemplateItem createOrderTemplateItem(Long templateId, Long productId, Integer quantity, Long substitutionPreferenceId) {
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try {
            final String hql1 = """
                        insert into OrderTemplateItem (orderTemplate, product, quantity, substitutionPreference)
                        select t, p, :quantity, s from OrderTemplate t, Product p, Product s
                        where t.id = :templateId and p.id = :productId and s.id = :substitutionPreferenceId
                    """;
            final String hql2 = """
                        insert into OrderTemplateItem (orderTemplate, product, quantity)
                        select t, p, :quantity from OrderTemplate t, Product p
                        where t.id = :templateId and p.id = :productId
                    """;
            Query query = substitutionPreferenceId == null ? session.createQuery(hql2) : session.createQuery(hql1);
            query.setParameter("templateId", templateId);
            query.setParameter("productId", productId);
            query.setParameter("quantity", quantity);
            if(substitutionPreferenceId != null) query.setParameter("substitutionPreferenceId", substitutionPreferenceId);
            query.executeUpdate();
            tx.commit();
            return findOrderTemplateItem(templateId, productId);
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public OrderTemplateItem updateOrderTemplateItem(Long id, Integer quantity, Long substitutionPreferenceId){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                update OrderTemplateItem set quantity = :quantity, substitutionPreference.id = :substitutionPreferenceId where id = :id
            """;
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            query.setParameter("quantity", quantity);
            query.setParameter("substitutionPreferenceId", substitutionPreferenceId);
            query.executeUpdate();
            tx.commit();
            session.evict(findById(id, OrderTemplateItem.class));
            return findById(id, OrderTemplateItem.class);
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public void createTemplateToOrder(Long orderId, Long templateId){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            session.save(TemplateToOrder.builder()
                            .orderId(orderId).templateId(templateId)
                            .createdDate(LocalDateTime.now())
                    .build());
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

//    @Transactional
    public ProductReferral findReferralByProduct(Long referredId, Long productId){
        try {
            var session = getCurrentSession();
            final String hql = """
                    from ProductReferral where referredUser.id = :referredId
                    and product.id = :productId and rewardCash is null
                """;
            var query = session.createQuery(hql, ProductReferral.class);
            query.setParameter("productId", productId);
            query.setParameter("referredId", referredId);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean createProductReferral(Long senderId, Long referredId, Long productId){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                insert into ProductReferral(sender, referredUser, product) select s, r, p
                from User s, User r, Product p where s.id = :senderId and r.id = :referredId and p.id = :productId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("senderId", senderId);
            query.setParameter("productId", productId);
            query.setParameter("referredId", referredId);
            query.executeUpdate();
            tx.commit();
            return true;
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public void updateProductReferral(Long referredId, Long productId, Float rewardCash){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                update ProductReferral set purchasedDate = now(), rewardCash = :rewardCash
                where referredUser.id = :referredId and product.id = :productId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("rewardCash", rewardCash);
            query.setParameter("productId", productId);
            query.setParameter("referredId", referredId);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
