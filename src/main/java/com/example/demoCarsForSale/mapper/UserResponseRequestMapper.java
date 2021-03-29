package com.example.demoCarsForSale.mapper;

import com.example.demoCarsForSale.controllers.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserExtraInfoResponse;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.dao.model.UserExtraInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserResponseRequestMapper {

    public static User convertUserSignUpRequestToUser(UserSignUpRequest user) {
        return User.builder()
            .name(user.getUserName())
            .email(user.getUserEmail())
            .password(user.getUserPassword())
            .build();
    }

    public static UserResponse convertUserToUserResponse(User user) {
        return UserResponse.builder()
            .userId(user.getId())
            .userName(user.getName())
            .build();
    }

    public static List<UserResponse> convertUserListToUserListResponse(List<User> users) {
        return users.stream()
            .map(UserResponseRequestMapper::convertUserToUserResponse)
            .collect(Collectors.toList());
    }

    public static List<UserExtraInfoResponse> convertUserExtraToUserExtraResponse(List<UserExtraInfo> userExtraInfos) {
        return userExtraInfos.stream()
            .map(x ->
                UserExtraInfoResponse.builder()
                    .userName(x.getName())
                    .email(x.getEmail())
                    .adCount(x.getAdCount())
                    .build())
            .collect(Collectors.toList());
    }
}
