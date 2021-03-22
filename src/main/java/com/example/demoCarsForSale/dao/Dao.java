package com.example.demoCarsForSale.dao;

import java.sql.SQLException;

public interface Dao<T> {
    T save(T model) throws SQLException;

    T get(Object id) throws SQLException;

    void update(T model) throws SQLException;

    void delete(Object id) throws SQLException;
}
