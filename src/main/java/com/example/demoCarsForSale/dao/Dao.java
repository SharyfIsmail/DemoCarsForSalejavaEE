package com.example.demoCarsForSale.dao;

import com.example.demoCarsForSale.dao.db.ConnectionManager;
import com.example.demoCarsForSale.exceptions.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.example.demoCarsForSale.dao.db.ConnectionManager.getConnection;

@FunctionalInterface
public interface Dao<T> {
    default T save(T model) {
        getConnection().persist(model);
        return model;
    }

    default T findById(long id) {
        T entity = getConnection().find(getClassType(), id);

        if (entity == null) {
            getConnection().close();
            throw new EntityNotFoundException("Not found: " + getClassType(), HttpServletResponse.SC_NOT_FOUND);
        }
        return entity;
    }

    default void update(T model) {
        getConnection().merge(model);
    }

    default void delete(long id) {
        T entity = getConnection().find(getClassType(), id);
        getConnection().remove(entity);
    }

    default List<T> findAll() {
        EntityManager entityManager = getConnection();
        return entityManager
            .createQuery("FROM" + getClassType().getSimpleName(), getClassType())
            .getResultList();
    }

    default boolean existsById(long id) {
        EntityManager entityManager = getConnection();

        Long count = (Long) entityManager.createQuery("SELECT COUNT(entity) FROM " + getClassType().getSimpleName() + " entity WHERE entity.id = :id")
            .setParameter("id", id)
            .getSingleResult();

        return ((count.equals(0l)) ? false : true);
    }

    Class<T> getClassType();
}
