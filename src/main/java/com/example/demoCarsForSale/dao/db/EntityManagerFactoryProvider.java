package com.example.demoCarsForSale.dao.db;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityManagerFactoryProvider {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myPersistence");
    private static final ThreadLocal<EntityManager> THREAD_LOCAL = new ThreadLocal<>();

    public static EntityManager getEntityManager() {
        if (THREAD_LOCAL.get() == null) {
            THREAD_LOCAL.set(ENTITY_MANAGER_FACTORY.createEntityManager());
        }
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        if (getEntityManager() != null) {
            THREAD_LOCAL.remove();
        }
    }
}
