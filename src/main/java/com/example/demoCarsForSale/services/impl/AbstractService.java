package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.db.ConnectionManager;

public abstract class AbstractService {

    protected static void startTransaction() {
        ConnectionManager.getConnection().getTransaction().begin();
    }

    protected static void closeTransaction() {
        ConnectionManager.getConnection().getTransaction().commit();
        ConnectionManager.clear();
    }

    protected static void rollback() {
        ConnectionManager.getConnection().getTransaction().rollback();
        ConnectionManager.clear();
    }
}
