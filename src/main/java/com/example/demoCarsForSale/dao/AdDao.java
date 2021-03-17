package com.example.demoCarsForSale.dao;

import com.example.demoCarsForSale.dao.model.Ad;

import java.sql.SQLException;
import java.util.List;

public interface AdDao extends Dao<Ad> {
    List<Ad> getAllUserAds(long userId) throws SQLException;

    List<Ad> getRecords(int start, int total) throws SQLException;

    void updateEditDate(long ad) throws SQLException;

    default void delete(Ad id) throws SQLException {

    }
}
