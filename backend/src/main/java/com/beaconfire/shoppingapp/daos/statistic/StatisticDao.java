package com.beaconfire.shoppingapp.daos.statistic;

import com.beaconfire.shoppingapp.daos.AbstractHibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import java.util.Arrays;
import java.util.List;

@Repository
public class StatisticDao extends AbstractHibernateDao {
    private EntityManager entityManager;

    @Autowired
    private void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public List<String> callStoredProcedure(String procedureName, List<Long> inputs){
        var query = entityManager.createStoredProcedureQuery(procedureName);
        for(int i = 0; i < inputs.size(); i++){
            query.registerStoredProcedureParameter(i + 1, Long.class, ParameterMode.IN);
            query.setParameter(i + 1, inputs.get(i));
        }

        query.execute();
        List<Object> res = query.getResultList();
        return res.stream().map(Object::toString).toList();
    }

    public List<String> mostPurchasedProducts(String procedureName, Long userId, Integer limit){
        var query = entityManager.createStoredProcedureQuery(procedureName);
        query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        query.setParameter(1, userId);
        query.setParameter(2, limit);

        query.execute();
        List<Object> res = query.getResultList();
        return res.stream().map(Object::toString).toList();
    }

    public List<String> mostPurchasedProductsForSeller(String procedureName, Integer limit){
        var query = entityManager.createStoredProcedureQuery(procedureName);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.setParameter(1, limit);

        query.execute();
        List<Object> res = query.getResultList();
        return res.stream().map(Object::toString).toList();
    }
}
