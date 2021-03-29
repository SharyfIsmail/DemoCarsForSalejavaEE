package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.controllers.dto.request.UserUpdateRequest;

@FunctionalInterface
public interface UserUpdateHandler {

    void updateUser(UserUpdateRequest userUpdateRequest, long userId);

    default boolean checkNewPassword(UserUpdateRequest userUpdateRequest) {
        return userUpdateRequest.getUserPassword1() != null &&
            userUpdateRequest.getUserPassword2() != null &&
            userUpdateRequest.getUserPassword1().equals(userUpdateRequest.getUserPassword2());
    }
}
