package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractDao {
    protected static PreparedStatement preparedStatement(String sqlCommand, int statement) throws SQLException {
        return ConnectionManager.getConnection().prepareStatement(sqlCommand, statement);
    }
}
