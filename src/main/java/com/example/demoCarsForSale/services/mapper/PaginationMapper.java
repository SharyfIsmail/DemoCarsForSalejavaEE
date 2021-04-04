package com.example.demoCarsForSale.services.mapper;

import com.example.demoCarsForSale.web.dto.projection.AdShortInfo;
import com.example.demoCarsForSale.web.dto.response.AdResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationMapper {
    public static List<AdResponse> toResponses(List<AdShortInfo> adShortInfos) {
        return adShortInfos.stream()
            .map(x -> AdResponse.builder()
                .adId(x.getAdId())
                .condition(x.getCondition().toString())
                .year(x.getYear())
                .brand(x.getBrand())
                .userName(x.getUserName())
                .model(x.getModel())
                .createDate(x.getCreateDate().toString())
                .picCount(x.getPicSize())
                .build())
            .collect(Collectors.toList());
    }
}
