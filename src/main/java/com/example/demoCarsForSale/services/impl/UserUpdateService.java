package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.controllers.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.impl.UserDaoImpl;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.mapper.UserUpdaterMapper;
import com.example.demoCarsForSale.services.UserUpdateHandler;

import javax.servlet.http.HttpServletResponse;

public class UserUpdateService extends AbstractService implements UserUpdateHandler {
    private static final UserDao USER_UPDATE = new UserDaoImpl();

    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest, long userId) {
        try {
            if (checkNewPassword(userUpdateRequest)) {
                startTransaction();

                User user = USER_UPDATE.findById(userId);
                UserUpdaterMapper.getDataFromUserRequestUpdateToUser(userUpdateRequest, user);
                USER_UPDATE.update(user);
                closeTransaction();
            } else {
                throw new BadRequestException("Passwords don't match", HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            rollback();
            throw new BadRequestException("Need to set all the data to update", e, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
