package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.controllers.dto.request.AdRequest;
import com.example.demoCarsForSale.controllers.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.controllers.dto.response.AdResponse;
import com.example.demoCarsForSale.dao.model.Ad;

import java.util.List;

public interface AdService {

    AdDetailedResponse save(AdRequest model, long userId);

    AdDetailedResponse get(long id);

    void delete(Ad id);

    List<AdResponse> getRecords(int page, int total);
}
