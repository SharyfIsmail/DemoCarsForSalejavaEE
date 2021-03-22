package com.example.demoCarsForSale.dao;

import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Pagination;

import java.sql.SQLException;
import java.util.List;

public interface AdDao extends Dao<Ad> {
    List<Ad> getAllUserAds(long userId) throws SQLException;

    List<Pagination> getRecords(int start, int total) throws SQLException;

    void updateEditDate(long ad) throws SQLException;
}
