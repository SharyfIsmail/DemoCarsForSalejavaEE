package com.example.demoCarsForSale.services.mapper;

import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.web.dto.request.AdRequest;
import com.example.demoCarsForSale.web.dto.response.AdDetailedResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdResponseRequestMapper {

    public static Ad toAd(AdRequest adRequest) {
        return Ad.builder()
            .year(adRequest.getYear())
            .brand(adRequest.getBrand())
            .model(adRequest.getModel())
            .engineCapacity(adRequest.getEngineCapacity())
            .condition(adRequest.getCondition())
            .power(adRequest.getPower())
            .mileage(adRequest.getMileAge())
            .createDate(LocalDateTime.now())
            .editDate(LocalDateTime.now())
            .build();
    }

    public static AdDetailedResponse toDetailedResponse(Ad ad) {
        return AdDetailedResponse.builder()
            .adId(ad.getAdId())
            .userName(ad.getUser().getName())
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
