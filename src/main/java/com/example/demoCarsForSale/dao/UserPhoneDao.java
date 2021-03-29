package com.example.demoCarsForSale.dao;

import com.example.demoCarsForSale.dao.model.UserPhone;

import java.util.List;

public interface UserPhoneDao extends Dao<UserPhone> {

    List<UserPhone> savePhones(List<UserPhone> pics);
}
