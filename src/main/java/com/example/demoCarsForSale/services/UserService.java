package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.controllers.dto.UserDto;

public interface UserService {
    UserDto save(UserDto userDto);

    UserDto get(long id);

    void update(UserDto userDto);

    void delete(long id);
}
