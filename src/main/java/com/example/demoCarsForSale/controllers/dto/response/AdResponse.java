package com.example.demoCarsForSale.controllers.dto.response;

import com.example.demoCarsForSale.dao.model.Condition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AdResponse {
    private long adId;
    private int year;
    private String brand;
    private String model;
    private Condition condition;
    private String userName;
    private int picCount;
    private String createDate;
}
