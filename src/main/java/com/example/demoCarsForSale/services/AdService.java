package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.web.dto.request.AdRequest;
import com.example.demoCarsForSale.web.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.web.dto.response.AdResponse;

import java.util.List;

public interface AdService {

    AdDetailedResponse createAd(AdRequest model, long userId);

    void deleteAd(long adId, long userId);

    AdDetailedResponse getDetailedInfoAboutAd(long id);

    List<AdResponse> getRecords(int start, int total);

    default boolean isAbleToDelete(long permissionId, long userId) {
        return permissionId == userId;
    }
}
