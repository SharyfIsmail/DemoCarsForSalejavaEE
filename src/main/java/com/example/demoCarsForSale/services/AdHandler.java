package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.controllers.dto.request.AdRequest;
import com.example.demoCarsForSale.controllers.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.controllers.dto.response.AdResponse;

import java.util.List;

public interface AdHandler {

    @SuppressWarnings("checkstyle:ParameterName")
    AdDetailedResponse createAd(AdRequest model, long userId);

    void deleteAd(long adId, long userId);

    AdDetailedResponse getDetailedInfoAboutAd(long id);

    List<AdResponse> getRecords(int start, int total);

    default boolean isAbleToDelete(long permissionId, long userId) {
        return permissionId == userId;
    }
}
