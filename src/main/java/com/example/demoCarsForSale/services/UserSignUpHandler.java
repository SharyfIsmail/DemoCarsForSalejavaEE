package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.controllers.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;

@FunctionalInterface
public interface UserSignUpHandler {

    UserResponse createUser(UserSignUpRequest userSignUpRequest);
}
