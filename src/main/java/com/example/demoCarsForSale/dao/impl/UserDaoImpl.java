package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.db.ConnectionManager;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.dao.model.UserExtraInfo;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {

    @Override
    public User findByEmail(String email) {
        return entityManager()
            .createQuery("FROM User user where user.email = :email", getClassType())
            .setParameter("email", email)
            .getSingleResult();
    }

    @Override
    public boolean existsByEmail(String email) {
        EntityManager entityManager = ConnectionManager.getConnection();

        if (email == null) {
            return false;
        }

        Long count = (Long) entityManager.createQuery("SELECT COUNT(entity.email) FROM User entity WHERE entity.email = :email")
            .setParameter("email", email)
            .getSingleResult();

        return (count.equals(0L) ? false : true);
    }

    @Override
    public List<UserExtraInfo> findAllWithExtraInfo() {
        EntityManager entityManager = entityManager();

        return entityManager.createQuery("SELECT NEW com.example.demoCarsForSale.dao.model.UserExtraInfo" +
            "(user.name, user.email, ad.size)" +
            " FROM User user " +
            " LEFT JOIN user.ads ad", UserExtraInfo.class)
            .getResultList();
    }

    @Override
    public User findUserWithPhones(long id) {
        EntityManager entityManager = entityManager();

        return entityManager.createQuery("SELECT user FROM User user" +
            " LEFT JOIN FETCH user.userPhones WHERE user.id =:id", User.class)
            .setParameter("id", id)
            .getSingleResult();
    }

    @Override
    public Class<User> getClassType() {
        return User.class;
    }
}
