package com.example.demoCarsForSale.dao.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
public class Pagination {
    private long adId;
    private int year;
    private String brand;
    private String model;
    private Condition condition;
    private String userName;
    private int picSize;
    private Timestamp createDate;
}
