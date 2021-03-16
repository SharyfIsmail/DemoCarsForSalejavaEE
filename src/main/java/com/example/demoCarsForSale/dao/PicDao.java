package com.example.demoCarsForSale.dao;

import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Pic;

import java.sql.SQLException;
import java.util.List;

public interface PicDao extends Dao<List<Pic>> {

    default void delete(Ad id) throws SQLException {
    }
}
