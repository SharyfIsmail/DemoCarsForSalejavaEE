package com.example.demoCarsForSale.mapper;

import com.example.demoCarsForSale.controllers.dto.UserDto;
import com.example.demoCarsForSale.dao.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static UserDto convertToDto(User user) {
        return UserDto.builder()
            .userName(user.getName())
            .userPassword(user.getPassword())
            .build();
    }

    public static User convertFromDto(UserDto userDto) {
        return User.builder()
            .name(userDto.getUserName())
            .password(userDto.getUserPassword())
            .build();
    }
}
