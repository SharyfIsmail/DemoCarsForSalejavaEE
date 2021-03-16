package com.example.demoCarsForSale.dao.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Pic {
    private long picId;
    private long adId;
    private String carPic;
}
