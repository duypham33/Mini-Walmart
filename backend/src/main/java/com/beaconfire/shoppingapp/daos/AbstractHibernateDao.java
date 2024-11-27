package com.beaconfire.shoppingapp.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateDao {
    protected SessionFactory sessionFactory;

    protected AbstractHibernateDao(){}

    @Autowired
    protected void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected <T> List<T> getAll(Class<T> clazz){
        var session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).getResultList();
    }

    protected <T, R extends Serializable> T findById(R id, Class<T> clazz) {
        try{
            return getCurrentSession().get(clazz, id);
        } catch (Exception e) {
            return null;
        }
    }

    protected <T> T add(T item) {
        getCurrentSession().save(item);
        return item;
    }

    protected <T> T merge(T item) {
        getCurrentSession().merge(item);
        return item;
    }

    protected <T> void delete(T item) {
        getCurrentSession().delete(item);
    }
}
