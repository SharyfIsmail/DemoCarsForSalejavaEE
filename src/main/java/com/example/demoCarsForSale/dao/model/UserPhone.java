package com.example.demoCarsForSale.dao.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserPhone {
    private long phoneId;
    private long userId;
    private String phone;
}
