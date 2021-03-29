package com.example.demoCarsForSale.controllers.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequest {
    private String userName;
    private String userEmail;
    private String userPassword1;
    private String userPassword2;
}
