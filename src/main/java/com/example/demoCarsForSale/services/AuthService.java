package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.controllers.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.controllers.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.model.User;

public interface AuthService {
    UserResponse save(UserSignUpRequest userLogInRequest);

    UserResponse get(UserLogInRequest userLogInRequest);

    default boolean checkAuth(UserLogInRequest userLogInRequest, User user) {
        return user.getPassword().equals(userLogInRequest.getUserPassword());
    }
}
