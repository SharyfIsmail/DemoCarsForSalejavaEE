package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.db.EntityManagerFactoryProvider;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.exceptions.EntityNotFoundException;
import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.services.mapper.UserResponseRequestMapper;
import com.example.demoCarsForSale.web.dto.projection.UserExtraInfo;
import com.example.demoCarsForSale.web.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.web.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.web.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.web.dto.response.UserExtraInfoResponse;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<UserResponse> findAll() {
        List<User> users = userDao.findAll();

        return UserResponseRequestMapper.toResponses(users);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<UserExtraInfoResponse> findUsersExtraInfo() {
        List<UserExtraInfo> userExtraInfos = userDao.findAllWithExtraInfo();

        return UserResponseRequestMapper.toExtrInfoResponses(userExtraInfos);
    }

    @Transactional
    @Override
    public void delete(long id) {
        try {
            userDao.delete(id);
        } catch (Exception e) {
            throw new BadRequestException("Couldn't not delete user");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public UserResponse logIn(UserLogInRequest userLogInRequest) {
        String email = userLogInRequest.getUserEmail();
        User userEntity;

        if (userDao.existsByEmail(email)) {
            userEntity = userDao.findByEmail(email);

            if (!checkAuth(userLogInRequest, userEntity)) {
                throw new BadRequestException("Incorrect password");
            }
        } else {
            throw new EntityNotFoundException("Wrong email");
        }

        return UserResponseRequestMapper.toResponse(userEntity);
    }

    @Transactional
    @Override
    public UserResponse createUser(UserSignUpRequest userSignUpRequest) {
        userSignUpRequest.setUserPassword(passwordEncoder().encode(userSignUpRequest.getUserPassword()));

        EntityManagerFactoryProvider.getEntityManager().getTransaction().begin();
        User userEntity = userDao.save(UserResponseRequestMapper
            .toUser(userSignUpRequest));

        EntityManagerFactoryProvider.getEntityManager().getTransaction().commit();
        EntityManagerFactoryProvider.clear();

        return UserResponseRequestMapper.toResponse(userEntity);
    }

    @Transactional
    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest, long userId) {
        if (checkNewPassword(userUpdateRequest)) {

            User user = userDao.findById(userId);
            setFieldsToUpdate(userUpdateRequest, user);
            userDao.update(user);
        } else {
            throw new BadRequestException("Passwords don't match");
        }
    }

    private static void setFieldsToUpdate(UserUpdateRequest requestUpdate, User toUpdate) {
        toUpdate.setEmail(requestUpdate.getUserEmail());
        toUpdate.setName(requestUpdate.getUserName());
        toUpdate.setPassword(requestUpdate.getUserPassword1());
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) {
        return userDao.findByEmail(email);
    }
}
