package com.example.demoCarsForSale.controllers.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserLogInRequest {
    private String userEmail;
    private String userPassword;
}
