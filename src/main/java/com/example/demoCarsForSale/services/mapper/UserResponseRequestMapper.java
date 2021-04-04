package com.example.demoCarsForSale.services.mapper;

import com.example.demoCarsForSale.web.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.web.dto.response.UserExtraInfoResponse;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.web.dto.projection.UserExtraInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserResponseRequestMapper {

    public static User toUser(UserSignUpRequest user) {
        return User.builder()
            .name(user.getUserName())
            .email(user.getUserEmail())
            .password(user.getUserPassword())
            .build();
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
            .userId(user.getUserId())
            .userName(user.getName())
            .build();
    }

    public static List<UserResponse> toResponses(List<User> users) {
        return users.stream()
            .map(UserResponseRequestMapper::toResponse)
            .collect(Collectors.toList());
    }

    public static List<UserExtraInfoResponse> toExtrInfoResponses(List<UserExtraInfo> userExtraInfos) {
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
