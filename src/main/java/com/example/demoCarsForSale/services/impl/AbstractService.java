package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.db.ConnectionManager;
import com.example.demoCarsForSale.exeptions.InternalErrorException;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public abstract class AbstractService {
    protected static void commit() throws SQLException {
        ConnectionManager.getConnection().commit();
        ConnectionManager.getConnection().close();
    }

    protected static void rollback() {
        try {
            ConnectionManager.getConnection().rollback();
        } catch (SQLException e) {
            throw new InternalErrorException("Couldn't rollback", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
