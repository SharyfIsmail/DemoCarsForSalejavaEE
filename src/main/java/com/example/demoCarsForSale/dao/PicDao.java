package com.example.demoCarsForSale.dao;

import com.example.demoCarsForSale.dao.model.Pic;

import java.util.List;

public interface PicDao extends Dao<Pic> {

    List<Pic> savePics(List<Pic> pics);

    Pic getByIdWithAd(long id);
}
