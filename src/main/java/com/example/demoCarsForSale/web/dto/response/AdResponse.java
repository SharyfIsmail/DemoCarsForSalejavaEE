package com.example.demoCarsForSale.web.dto.response;

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
    private String condition;
    private String userName;
    private int picCount;
    private String createDate;
}
