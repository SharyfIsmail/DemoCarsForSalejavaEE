package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.dao.model.Pic;

import java.util.List;

public interface PicService {
    List<Pic> save(List<Pic> pics);

    List<Pic> get(Object adId);

    void delete(Object ad);
}
