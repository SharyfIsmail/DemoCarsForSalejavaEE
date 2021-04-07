package com.example.demoCarsForSale.web.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserSignUpRequest {
    @NotEmpty(message = "Name shouldn't be empty")
    private String userName;
    @NotEmpty(message = "email shouldn't be empty")
    @Size(min = 1, max = 25, message = "Email's length should be less than 25 characters")
    private String userEmail;
    @NotEmpty(message = "Password shouldn't be empty")
    @Size(min = 1, max = 15, message = "Password's length should be less than 15 characters")
    private String userPassword;
}

