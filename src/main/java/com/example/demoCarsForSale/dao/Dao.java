package com.example.demoCarsForSale.dao;

import com.example.demoCarsForSale.exceptions.EntityNotFoundException;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.demoCarsForSale.dao.db.EntityManagerFactoryProvider.getEntityManager;

@FunctionalInterface
public interface Dao<T> {
    default T save(T model) {
        getEntityManager().persist(model);
        return model;
    }

    default T findById(long id) {
        T entity = getEntityManager().find(getClassType(), id);

        if (entity == null) {
            throw new EntityNotFoundException("Not found: " + getClassType());
        }
        return entity;
    }

    default void update(T model) {
        getEntityManager().merge(model);
    }

    default void delete(long id) {
        T entity = getEntityManager().find(getClassType(), id);
        getEntityManager().remove(entity);
    }

    default List<T> findAll() {
        EntityManager entityManager = getEntityManager();
        return entityManager
            .createQuery("FROM" + getClassType().getSimpleName(), getClassType())
            .getResultList();
    }

    default boolean existsById(long id) {
        EntityManager entityManager = getEntityManager();

        Long count = (Long) entityManager.createQuery("SELECT COUNT(entity) FROM " + getClassType().getSimpleName() + " entity WHERE entity.id = :id")
            .setParameter("id", id)
            .getSingleResult();

        return !count.equals(0L);
    }

    Class<T> getClassType();
}
