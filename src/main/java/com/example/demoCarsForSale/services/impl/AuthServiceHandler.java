package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.controllers.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.controllers.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.impl.UserLogInImpl;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.exeptions.BadRequestException;
import com.example.demoCarsForSale.exeptions.InternalErrorException;
import com.example.demoCarsForSale.mapper.UserResponseRequestMapper;
import com.example.demoCarsForSale.services.AuthService;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public final class AuthServiceHandler extends AbstractService implements AuthService {
    private static final UserDao LOG_IN = new UserLogInImpl();

    @Override
    public UserResponse save(UserSignUpRequest userSignUpRequest) {
        UserResponse userLogInResponse;

        try {
            User user = LOG_IN.save(UserResponseRequestMapper.convertUserSignUpRequestToUser(userSignUpRequest));
            commit();
            userLogInResponse = UserResponseRequestMapper.convertUserToUserResponse(user);
        } catch (SQLException e) {
            rollback();
            throw new BadRequestException("Make sure to type your name," +
                " your email and your password to sign up", e, HttpServletResponse.SC_BAD_REQUEST);
        }
        return userLogInResponse;
    }

    @Override
    public UserResponse get(UserLogInRequest userLogInRequest) {
        UserResponse userLogInResponse;

        try {
            User user = LOG_IN.get(UserResponseRequestMapper.convertUserLogInRequestToUser(userLogInRequest).getEmail());
            userLogInResponse = UserResponseRequestMapper.convertUserToUserResponse(user);

            if (!checkAuth(userLogInRequest, user)) {
                throw new BadRequestException("Incorrect password", HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new InternalErrorException("Internal error", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return userLogInResponse;
    }
}
