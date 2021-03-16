package com.example.demoCarsForSale.mapper;

import com.example.demoCarsForSale.controllers.dto.request.AdRequest;
import com.example.demoCarsForSale.controllers.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.dao.model.Ad;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdResponseRequestMapper {

    public static Ad convertUserAdRequestToAd(AdRequest adRequest, long userId) {
        return Ad.builder()
            .userId(userId)
            .year(adRequest.getYear())
            .brand(adRequest.getBrand())
            .model(adRequest.getModel())
            .engineCapacity(adRequest.getEngineCapacity())
            .condition(adRequest.getCondition())
            .mileage(adRequest.getMileAge())
            .createDate(new Timestamp(System.currentTimeMillis()))
            .editDate(new Timestamp(System.currentTimeMillis()))
            .build();
    }

    public static AdDetailedResponse convertAdToAdDetailedResponse(Ad ad) {
        return AdDetailedResponse.builder()
            .adId(ad.getAdId())
            .year(ad.getYear())
            .brand(ad.getBrand())
            .model(ad.getModel())
            .engineCapacity(ad.getEngineCapacity())
            .condition(ad.getCondition())
            .power(ad.getPower())
            .createDate(ad.getCreateDate().toString())
            .editDate(ad.getEditDate().toString())
            .build();
    }
}
