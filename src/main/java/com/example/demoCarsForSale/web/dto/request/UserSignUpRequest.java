package com.example.demoCarsForSale.web.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserSignUpRequest {
    private String userName;
    private String userEmail;
    private String userPassword;
}

