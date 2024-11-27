package com.beaconfire.shoppingapp.daos.order;

import com.beaconfire.shoppingapp.daos.AbstractHibernateDao;
import com.beaconfire.shoppingapp.dtos.queryFeature.QueryPage;
import com.beaconfire.shoppingapp.entities.order.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao extends AbstractHibernateDao {
    public Invoice createInvoice(){
        var session = getCurrentSession();
        Invoice invoice = Invoice.builder().status(Invoice.Status.PAID).build();
        session.save(invoice);
        return invoice;
    }

    public Shipping createShipping(LocalDateTime estimatedDate){
        var session = getCurrentSession();
        Shipping shipping = Shipping.builder().estimatedDate(estimatedDate).build();
        session.save(shipping);
        return shipping;
    }

    public void createInvoiceItems(Invoice invoice, List<InvoiceItem> invoiceItems){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            invoice.setItems(new ArrayList<>());
            for(var item : invoiceItems){
                invoice.getItems().add(item);
                item.setInvoice(invoice);
            }
            System.out.println(invoice);
            System.out.println(invoiceItems);
            session.merge(invoice);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void createInvoiceItem(Long invoiceId, String detail, Float subtotal){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                insert into InvoiceItem (invoice, detail, subtotal)
                select i, :detail, :subtotal
                from Invoice i where i.id = :invoiceId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("invoiceId", invoiceId);
            query.setParameter("detail", detail);
            query.setParameter("subtotal", subtotal);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

//    public List<Order> getOrdersForSeller(
//            QueryPage queryPage, String status
//    ){
//        var session = getCurrentSession();
//        var criteriaBuilder = session.getCriteriaBuilder();
//        var criteriaQuery = criteriaBuilder.createQuery(Order.class);
//        Root<Order> orderRoot = criteriaQuery.from(Order.class);
//
//        if(status != null){
//            criteriaQuery.where(criteriaBuilder.equal(orderRoot.get("status"), status));
//        }
//
//        criteriaQuery.orderBy(criteriaBuilder.desc(orderRoot.get("id")));
//        Query<Order> query = session.createQuery(criteriaQuery);
//        query.setFirstResult(queryPage == null ? 0 : queryPage.getSkip());
//        query.setMaxResults(queryPage == null ? 5 : queryPage.getPageSize());
//        return query.getResultList();
//    }

    public List<Order> getOrdersByUserId(Long userId, QueryPage queryPage, Order.Status status){
        try{
            Session session = getCurrentSession();
            final String hql = """
                from Order o where o.user.id = :userId order by o.id desc
            """;
            final String hql2 = """
                from Order o where o.user.id = :userId and o.status = :status order by o.id desc
            """;

            Query<Order> query = session.createQuery(status == null ? hql : hql2, Order.class);
            query.setParameter("userId", userId);
            if(status != null) query.setParameter("status", status);
            query.setFirstResult(queryPage == null ? 0 : queryPage.getSkip());
            query.setMaxResults(queryPage == null ? 5 : queryPage.getPageSize());
            return query.getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Order getOrderById(Long orderId){
        return findById(orderId, Order.class);
    }

    public Order getOrderFromInvoiceId(Long invoiceId){
        try{
            Session session = getCurrentSession();
            final String hql = """
                from Order o where o.invoice.id = :invoiceId
            """;
            Query<Order> query = session.createQuery(hql, Order.class);
            query.setParameter("invoiceId", invoiceId);
            return query.getSingleResult();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Order createOrder(Long userId, Long invoiceId, Long shippingId, String recordedLog, Float total, Float profit){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                insert into Order (user, invoice, shipping, recordedLog, total, profit) select u, i, s, :recordedLog, :total, :profit
                from User u, Invoice i, Shipping s where u.id = :userId and i.id = :invoiceId and s.id = :shippingId
            """;

            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("invoiceId", invoiceId);
            query.setParameter("shippingId", shippingId);
            query.setParameter("recordedLog", recordedLog);
            query.setParameter("total", total);
            query.setParameter("profit", profit);
            query.executeUpdate();
            tx.commit();

            return getOrderFromInvoiceId(invoiceId);
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public void updateOrder(Long orderId, Order.Status status, String recordedLog){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                update Order set status = :status, recordedLog = :recordedLog where id = :orderId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("orderId", orderId);
            query.setParameter("status", status);
            query.setParameter("recordedLog", recordedLog);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void updateShipping(Long id, Shipping.Status status){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                update Shipping set status = :status where id = :id
            """;
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            query.setParameter("status", status);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }



    public void createOrderItems(Order order, List<OrderItem> orderItems){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            for(var item : orderItems){
                order.getItems().add(item);
                item.setShoppingOder(order);
            }
            session.saveOrUpdate(order);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void createOrderItem(Long orderId, Long productId, Integer quantity, Double purchasedPrice, Double wholesalePrice){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                insert into OrderItem (shoppingOder, product, quantity, purchasedPrice, wholesalePrice)
                select o, p, :quantity, :purchasedPrice, :wholesalePrice
                from Order o, Product p where o.id = :orderId and p.id = :productId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("orderId", orderId);
            query.setParameter("productId", productId);
            query.setParameter("quantity", quantity);
            query.setParameter("purchasedPrice", purchasedPrice);
            query.setParameter("wholesalePrice", wholesalePrice);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void updateOrderItemsStatus(Long orderId, Boolean isCompleted){
        Session session = getCurrentSession();
        var tx = session.beginTransaction();
        try{
            final String hql = """
                update OrderItem set isCompleted = :isCompleted where shoppingOder.id = :orderId
            """;
            Query query = session.createQuery(hql);
            query.setParameter("orderId", orderId);
            query.setParameter("isCompleted", isCompleted);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Order> getOrdersForSeller(
            QueryPage queryPage, Order.Status status
    ){
        var session = getCurrentSession();
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);

        if(status != null){
            criteriaQuery.where(criteriaBuilder.equal(orderRoot.get("status"), status));
        }

        criteriaQuery.orderBy(criteriaBuilder.desc(orderRoot.get("id")));
        Query<Order> query = session.createQuery(criteriaQuery);
        query.setFirstResult(queryPage == null ? 0 : queryPage.getSkip());
        query.setMaxResults(queryPage == null ? 5 : queryPage.getPageSize());
        return query.getResultList();
    }
}
