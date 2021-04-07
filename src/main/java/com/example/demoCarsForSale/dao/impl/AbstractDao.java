package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public abstract class AbstractDao<T> implements Dao<T> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}

