package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.web.dto.request.AdRequest;
import com.example.demoCarsForSale.web.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.web.dto.response.AdResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Supplier;

public interface AdService {

    AdDetailedResponse createAd(AdRequest model, long userId);

    void deleteAd(long adId, long userId);

    AdDetailedResponse getDetailedInfoAboutAd(long id);

    List<AdResponse> getRecords(Pageable pageable);

    default boolean isAbleToDelete(long permissionId, long userId) {
        return permissionId == userId;
    }

    default void isValidAction(boolean isValid, Supplier<? extends RuntimeException> supplier) {
        if (!isValid) {
            supplier.get();
        }
    }
}
