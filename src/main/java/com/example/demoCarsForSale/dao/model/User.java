package com.example.demoCarsForSale.dao.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User {
    private long id;
    private String name;
    private String email;
    private String password;
}
