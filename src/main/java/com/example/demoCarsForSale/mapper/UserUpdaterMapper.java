package com.example.demoCarsForSale.mapper;

import com.example.demoCarsForSale.controllers.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.dao.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserUpdaterMapper {

    public static void getDataFromUserRequestUpdateToUser(UserUpdateRequest userUpdateRequest, User user) {
        user.setEmail(userUpdateRequest.getUserEmail());
        user.setName(userUpdateRequest.getUserName());
        user.setPassword(userUpdateRequest.getUserPassword1());
    }
}
