package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.db.EntityManagerFactoryProvider;

import javax.persistence.EntityManager;

public abstract class AbstractDao {

    protected static EntityManager entityManager() {
        return EntityManagerFactoryProvider.getEntityManager();
    }
}

