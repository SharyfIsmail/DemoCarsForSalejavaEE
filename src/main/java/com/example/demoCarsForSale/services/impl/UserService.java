package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.controllers.dto.response.UserExtraInfoResponse;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.impl.UserDaoImpl;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.dao.model.UserExtraInfo;
import com.example.demoCarsForSale.mapper.UserResponseRequestMapper;
import com.example.demoCarsForSale.services.UserHandler;

import java.util.List;

public final class UserService extends AbstractService implements UserHandler {
    private static final UserDao USER = new UserDaoImpl();

    @Override
    public List<UserResponse> findAll() {
        startTransaction();
        List<User> users = USER.findAll();
        closeTransaction();

        return UserResponseRequestMapper.convertUserListToUserListResponse(users);
    }

    @Override
    public List<UserExtraInfoResponse> findUserExtraInfo() {
        startTransaction();
        List<UserExtraInfo> userExtraInfos = USER.findAllWithExtraInfo();
        closeTransaction();

        return UserResponseRequestMapper.convertUserExtraToUserExtraResponse(userExtraInfos);
    }
}
