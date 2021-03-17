package com.example.demoCarsForSale.mapper;

import com.example.demoCarsForSale.controllers.dto.request.PicRequest;
import com.example.demoCarsForSale.controllers.dto.response.PicResponse;
import com.example.demoCarsForSale.dao.model.Pic;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PicRequestResponseMapper {

    public static List<Pic> convertPicRequestToPic(List<PicRequest> pics, long adId) {
        return pics.stream()
            .filter(x -> x.getPic() != null)
            .map(x -> {
                return Pic.builder()
                    .adId(adId)
                    .carPic(x.getPic())
                    .build();
            })
            .collect(Collectors.toList());
    }

    public static List<PicResponse> convertPicToPicResponse(List<Pic> pics) {
        return pics.stream()
            .map(x -> {
                return PicResponse.builder()
                    .pic(x.getCarPic())
                    .build();
            })
            .collect(Collectors.toList());
    }
}
