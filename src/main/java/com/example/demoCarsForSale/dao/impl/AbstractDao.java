package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.db.ConnectionManager;

import javax.persistence.EntityManager;

public abstract class AbstractDao {

    protected static EntityManager entityManager() {
        return ConnectionManager.getConnection();
    }
}

