package com.example.demoCarsForSale.web.dto.projection;

import org.springframework.beans.factory.annotation.Value;

public interface UserExtraInfo {
    String getName();

    String getEmail();

    @Value("#{target.ads.size}")
    int getAds();
}
