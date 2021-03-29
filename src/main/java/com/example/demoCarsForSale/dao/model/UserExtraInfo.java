package com.example.demoCarsForSale.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserExtraInfo {
    private String name;
    private String email;
    private int adCount;
}
