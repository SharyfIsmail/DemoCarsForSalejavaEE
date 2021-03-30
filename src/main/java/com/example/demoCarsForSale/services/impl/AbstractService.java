package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.db.EntityManagerFactoryProvider;

public abstract class AbstractService {

    protected static void startTransaction() {
        EntityManagerFactoryProvider.getEntityManager().getTransaction().begin();
    }

    protected static void closeTransaction() {
        EntityManagerFactoryProvider.getEntityManager().getTransaction().commit();
        EntityManagerFactoryProvider.clear();
    }

    protected static void rollback() {
        EntityManagerFactoryProvider.getEntityManager().getTransaction().rollback();
        EntityManagerFactoryProvider.clear();
    }
}
