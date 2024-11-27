package com.beaconfire.shoppingapp.daos.account;

import com.beaconfire.shoppingapp.daos.AbstractHibernateDao;
import com.beaconfire.shoppingapp.entities.account.user.User;
import com.beaconfire.shoppingapp.entities.account.user.UserRole;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractHibernateDao {
    public User getByUsername(String username){
        try{
            Session session = getCurrentSession();
            final String hql = "from User u where u.username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User getByEmail(String email){
        try{
            Session session = getCurrentSession();
            final String hql = "from User u where u.email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User getById(Long userId){
        return findById(userId, User.class);
    }

    public User createUser(String username, String email, String hashPassword){
        var session = getCurrentSession();
        UserRole role = findById("REGULAR", UserRole.class);
        var user = User.builder().username(username).email(email).hashPassword(hashPassword).build();
        user.setRole(role);
        session.save(user);
        return user;
    }
}
