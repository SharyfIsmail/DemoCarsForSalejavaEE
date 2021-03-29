package com.example.demoCarsForSale.dao;

import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.dao.model.UserExtraInfo;

import java.util.List;

public interface UserDao extends Dao<User> {

    boolean existsByEmail(String email);

    User findByEmail(String email);

    List<UserExtraInfo> findAllWithExtraInfo();

    User findUserWithPhones(long id);
}
