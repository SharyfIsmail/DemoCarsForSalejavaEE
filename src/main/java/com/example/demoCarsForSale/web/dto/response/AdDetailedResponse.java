package com.example.demoCarsForSale.web.dto.response;

import com.example.demoCarsForSale.pojo.Condition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
