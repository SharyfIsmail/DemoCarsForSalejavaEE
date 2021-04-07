package com.example.demoCarsForSale.dao;

import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Pic;
import com.example.demoCarsForSale.web.dto.projection.AdShortInfo;

import java.util.List;

public interface AdDao extends Dao<Ad> {

    List<AdShortInfo> getRecords(int start, int total);

    Ad getDetailedInfoAboutAd(long id);

    void deletePicFromAd(Pic pic);
}
