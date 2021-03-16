package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Pic;

import java.io.Serializable;
import java.util.List;

public interface PicService {
    List<Pic> save(List<Pic> pics);

    List<Pic> get(Serializable adId);

    void delete(Ad ad);
}
