package com.example.demoCarsForSale.web.dto.projection;

import com.example.demoCarsForSale.dao.model.Condition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class AdShortInfo {
    private long adId;
    private int year;
    private String brand;
    private String model;
    private Condition condition;
    private String userName;
    private int picSize;
    private LocalDateTime createDate;
}
