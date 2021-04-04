package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.web.dto.projection.UserExtraInfo;
import com.example.demoCarsForSale.web.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.web.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.web.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.web.dto.response.UserExtraInfoResponse;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.impl.UserDaoImpl;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.exceptions.EntityNotFoundException;
import com.example.demoCarsForSale.services.mapper.UserResponseRequestMapper;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class UserServiceImpl extends AbstractService implements com.example.demoCarsForSale.services.UserService {
    private static final UserDao USER = new UserDaoImpl();

    @Override
    public List<UserResponse> findAll() {
        startTransaction();
        List<User> users = USER.findAll();
        closeTransaction();

        return UserResponseRequestMapper.toResponses(users);
    }

    @Override
    public List<UserExtraInfoResponse> findUserExtraInfo() {
        startTransaction();
        List<UserExtraInfo> userExtraInfos = USER.findAllWithExtraInfo();
        closeTransaction();

        return UserResponseRequestMapper.toExtrInfoResponses(userExtraInfos);
    }

    @Override
    public void delete(long id) {
        try {
            startTransaction();
            USER.delete(id);
            closeTransaction();
        } catch (Exception e) {
            rollback();

            throw new BadRequestException("Couldn't not delete user", e, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public UserResponse logIn(UserLogInRequest userLogInRequest) {
        String email = userLogInRequest.getUserEmail();

        startTransaction();
        User userEntity;
        if (USER.existsByEmail(email)) {
            userEntity = USER.findByEmail(email);
            closeTransaction();

            if (!checkAuth(userLogInRequest, userEntity)) {
                throw new BadRequestException("Incorrect password", HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            rollback();
            throw new EntityNotFoundException("Wrong email", HttpServletResponse.SC_NOT_FOUND);
        }

        return UserResponseRequestMapper.toResponse(userEntity);
    }

    @Override
    public UserResponse createUser(UserSignUpRequest userSignUpRequest) {
        User userEntity;

        try {
            startTransaction();
            userEntity = USER.save(UserResponseRequestMapper
                .toUser(userSignUpRequest));

            closeTransaction();
        } catch (RuntimeException e) {
            rollback();

            throw new BadRequestException("Please type your name, email and your password to sign up", e,
                HttpServletResponse.SC_BAD_REQUEST);
        }

        return UserResponseRequestMapper.toResponse(userEntity);
    }

    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest, long userId) {
        try {
            if (checkNewPassword(userUpdateRequest)) {
                startTransaction();

                User user = USER.findById(userId);
                setFieldsToUpdate(userUpdateRequest, user);
                USER.update(user);
                closeTransaction();
            } else {
                throw new BadRequestException("Passwords don't match", HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            rollback();
            throw new BadRequestException("Need to set all the data to update", e, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private static void setFieldsToUpdate(UserUpdateRequest requestUpdate, User toUpdate) {
        toUpdate.setEmail(requestUpdate.getUserEmail());
        toUpdate.setName(requestUpdate.getUserName());
        toUpdate.setPassword(requestUpdate.getUserPassword1());
    }
}

