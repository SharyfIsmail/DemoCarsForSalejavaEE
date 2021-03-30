package com.example.demoCarsForSale.services.mapper;

import com.example.demoCarsForSale.controllers.dto.request.PicRequest;
import com.example.demoCarsForSale.controllers.dto.response.PicResponse;
import com.example.demoCarsForSale.dao.model.Pic;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PicRequestResponseMapper {

    public static List<Pic> toPics(List<PicRequest> pics) {
        if (pics == null) {
            return Collections.emptyList();
        }

        return pics.stream()
            .filter(x -> x.getPic() != null)
            .map(x -> Pic.builder()
                .carPic(x.getPic())
                .build())
            .collect(Collectors.toList());
    }

    public static List<PicResponse> toResponses(List<Pic> pics) {
        return pics.stream()
            .map(x -> PicResponse.builder()
                .pic(x.getCarPic())
                .build())
            .collect(Collectors.toList());
    }
}
