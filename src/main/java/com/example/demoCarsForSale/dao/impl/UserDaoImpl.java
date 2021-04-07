package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.web.dto.projection.UserExtraInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    @Override
    public User findByEmail(String email) {
        return getEntityManager()
            .createQuery("SELECT user FROM User user where user.email = :email", getClassType())
            .setParameter("email", email)
            .getSingleResult();
    }

    @Override
    public boolean existsByEmail(String email) {
        EntityManager entityManager = getEntityManager();

        if (email == null) {
            return false;
        }

        Long count = (Long) entityManager.createQuery("SELECT COUNT(entity.email) FROM User entity WHERE entity.email = :email")
            .setParameter("email", email)
            .getSingleResult();

        return !count.equals(0L);
    }

    @Override
    public List<UserExtraInfo> findAllWithExtraInfo() {
        return getEntityManager().createQuery("SELECT DISTINCT NEW com.example.demoCarsForSale.web.dto.projection.UserExtraInfo" +
            "(user.name, user.email, ad.size)" +
            " FROM User user " +
            " LEFT JOIN user.ads ad", UserExtraInfo.class)
            .getResultList();
    }

    @Override
    public User findUserWithPhones(long id) {
        EntityManager entityManager = getEntityManager();

        return entityManager.createQuery("SELECT user FROM User user" +
            " LEFT JOIN FETCH user.userPhones WHERE user.userId =:id", User.class)
            .setParameter("id", id)
            .getSingleResult();
    }

    @Override
    public Class<User> getClassType() {
        return User.class;
    }
}
