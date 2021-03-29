package com.example.demoCarsForSale.mapper;

import com.example.demoCarsForSale.controllers.dto.response.AdResponse;
import com.example.demoCarsForSale.dao.model.Pagination;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationMapper {
    public static List<AdResponse> convertPaginationToAdResponse(List<Pagination> paginations) {
        return paginations.stream()
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
