package com.example.demoCarsForSale.web.dto.request;

import com.example.demoCarsForSale.dao.model.Condition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AdRequest {
    private int year;
    private String brand;
    private String model;
    private int engineCapacity;
    private int mileAge;
    private Condition condition;
    private int power;
    private List<PicRequest> pics;
    private List<PhoneRequest> phones;
}
