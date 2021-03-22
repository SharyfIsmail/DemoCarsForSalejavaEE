package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.dao.model.UserPhone;

import java.util.List;

public interface UserPhoneService {

    List<UserPhone> save(List<UserPhone> phones);

    List<UserPhone> get(Object userID);
}
