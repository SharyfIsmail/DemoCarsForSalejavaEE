package com.example.demoCarsForSale.web.dto.projection;

import com.example.demoCarsForSale.pojo.Condition;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface AdShortInfo {
    long getAdId();

    int getYear();

    String getBrand();

    String getModel();

    Condition getCondition();

    @Value("#{target.user.name}")
    String getUser();

    @Value("#{target.pics.size}")
    int getPics();

    LocalDateTime getCreateDate();
}
