package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.controllers.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.impl.UserDaoImpl;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.exceptions.EntityNotFoundException;
import com.example.demoCarsForSale.mapper.UserResponseRequestMapper;
import com.example.demoCarsForSale.services.UserLogInHandler;

import javax.servlet.http.HttpServletResponse;

public class UserLoginService extends AbstractService implements UserLogInHandler {
    private static final UserDao USER_LOGING = new UserDaoImpl();

    @Override
    public UserResponse logIn(UserLogInRequest userLogInRequest) {
        String email = userLogInRequest.getUserEmail();

        startTransaction();
        User userEntity;
        if (USER_LOGING.existsByEmail(email)) {
            userEntity = USER_LOGING.findByEmail(email);
            closeTransaction();

            if (!checkAuth(userLogInRequest, userEntity)) {
                throw new BadRequestException("Incorrect password", HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            rollback();
            throw new EntityNotFoundException("Wrong email", HttpServletResponse.SC_NOT_FOUND);
        }

        return UserResponseRequestMapper.convertUserToUserResponse(userEntity);
    }
}
