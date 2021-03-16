package com.example.demoCarsForSale.mapper;

import com.example.demoCarsForSale.controllers.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.controllers.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserResponseRequestMapper {

    public static User convertUserSignUpRequestToUser(UserSignUpRequest user) {
        return User.builder()
            .name(user.getUserName())
            .email(user.getUserEmail())
            .password(user.getUserPassword())
            .build();
    }

    public static User convertUserLogInRequestToUser(UserLogInRequest user) {
        return User.builder()
            .email(user.getUserEmail())
            .password(user.getUserPassword())
            .build();
    }

    public static UserResponse convertUserToUserResponse(User user) {
        return UserResponse.builder()
            .userName(user.getName())
            .build();
    }
}
