package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.db.ConnectionManager;

import java.sql.SQLException;

public abstract class AbstractService {
    protected static void commit() throws SQLException {
        ConnectionManager.getConnection().commit();
    }

    protected static void rollback() {
        try {
            ConnectionManager.getConnection().rollback();
        } catch (SQLException throwable) {
            throw new RuntimeException("Couldn't rollback");
        }
    }
}
