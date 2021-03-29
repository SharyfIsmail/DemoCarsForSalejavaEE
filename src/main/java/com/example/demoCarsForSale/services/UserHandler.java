package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.controllers.dto.response.UserExtraInfoResponse;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;

import java.util.List;

public interface UserHandler {

    List<UserResponse> findAll();

    List<UserExtraInfoResponse> findUserExtraInfo();
}
