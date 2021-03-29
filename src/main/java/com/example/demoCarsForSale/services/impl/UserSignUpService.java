package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.controllers.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.impl.UserDaoImpl;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.mapper.UserResponseRequestMapper;
import com.example.demoCarsForSale.services.UserSignUpHandler;

import javax.servlet.http.HttpServletResponse;

public class UserSignUpService extends AbstractService implements UserSignUpHandler {
    private static final UserDao USE_SIGNUP = new UserDaoImpl();

    @Override
    public UserResponse createUser(UserSignUpRequest userSignUpRequest) {
        User userEntity;

        try {
            startTransaction();
            userEntity = USE_SIGNUP.save(UserResponseRequestMapper
                .convertUserSignUpRequestToUser(userSignUpRequest));

            closeTransaction();
        } catch (RuntimeException e) {
            rollback();

            throw new BadRequestException("Please type your name, email and your password to sign up", e,
                HttpServletResponse.SC_BAD_REQUEST);
        }

        return UserResponseRequestMapper.convertUserToUserResponse(userEntity);
    }
}
