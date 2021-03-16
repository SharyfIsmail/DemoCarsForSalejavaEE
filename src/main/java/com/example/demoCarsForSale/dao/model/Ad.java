package com.example.demoCarsForSale.dao.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
public class Ad {
    private long adId;
    private long userId;
    private int year;
    private String brand;
    private String model;
    private int engineCapacity;
    private Condition condition;
    private int mileage;
    private int power;
    private Timestamp createDate;
    private Timestamp editDate;
}
