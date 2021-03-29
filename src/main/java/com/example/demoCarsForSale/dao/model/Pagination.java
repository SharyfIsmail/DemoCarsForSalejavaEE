package com.example.demoCarsForSale.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
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
    private LocalDateTime createDate;
}
