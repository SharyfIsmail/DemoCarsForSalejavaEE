package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.controllers.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.model.User;

@FunctionalInterface
public interface UserLogInHandler {

    UserResponse logIn(UserLogInRequest userLogInRequest);

    default boolean checkAuth(UserLogInRequest userLogInRequest, User user) {
        return user.getPassword().equals(userLogInRequest.getUserPassword());
    }
}
