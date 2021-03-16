package com.example.demoCarsForSale.controllers.dto.response;

import com.example.demoCarsForSale.dao.model.Condition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class AdDetailedResponse {
    private long adId;
    private int year;
    private String brand;
    private String model;
    private int engineCapacity;
    private Condition condition;
    private int power;
    private String userName;
    private List<PicResponse> pics;
    private List<PhoneResponse> phones;
    private String createDate;
    private String editDate;
}
