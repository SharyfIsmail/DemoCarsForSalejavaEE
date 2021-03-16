package com.example.demoCarsForSale.dao;

import java.io.Serializable;
import java.sql.SQLException;

public interface Dao<T> {
    T save(T model) throws SQLException;

    T get(Serializable id) throws SQLException;

    default void update(T model) throws SQLException {

    }

    default void delete(Serializable id) throws SQLException {

    }
}
