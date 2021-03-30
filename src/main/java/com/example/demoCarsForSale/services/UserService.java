package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.controllers.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.controllers.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.controllers.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserExtraInfoResponse;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.model.User;

import java.util.List;

public interface UserService {

    List<UserResponse> findAll();

    List<UserExtraInfoResponse> findUserExtraInfo();

    void delete(long id);

    UserResponse logIn(UserLogInRequest userLogInRequest);

    UserResponse createUser(UserSignUpRequest userSignUpRequest);

    void updateUser(UserUpdateRequest userUpdateRequest, long userId);

    default boolean checkNewPassword(UserUpdateRequest userUpdateRequest) {
        return userUpdateRequest.getUserPassword1() != null &&
            userUpdateRequest.getUserPassword2() != null &&
            userUpdateRequest.getUserPassword1().equals(userUpdateRequest.getUserPassword2());
    }

    default boolean checkAuth(UserLogInRequest userLogInRequest, User user) {
        return user.getPassword().equals(userLogInRequest.getUserPassword());
    }
}
