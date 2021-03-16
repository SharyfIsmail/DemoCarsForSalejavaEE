package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.UserPhoneDao;
import com.example.demoCarsForSale.dao.impl.AbstractDao;
import com.example.demoCarsForSale.dao.impl.UserPhoneDaoImpl;
import com.example.demoCarsForSale.dao.model.UserPhone;
import com.example.demoCarsForSale.exeptions.BadRequestException;
import com.example.demoCarsForSale.exeptions.InternalErrorException;
import com.example.demoCarsForSale.services.UserPhoneService;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class UserPhoneServiceHandler extends AbstractService implements UserPhoneService {
    private static final UserPhoneDao USER_PHONE_DAO = new UserPhoneDaoImpl();

    @Override
    public List<UserPhone> save(List<UserPhone> phones) {
        try {
            return USER_PHONE_DAO.save(phones);
        } catch (SQLException e) {
            rollback();
            throw new BadRequestException(e.getMessage(), e, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public List<UserPhone> get(Serializable userID) {
        try {
            return USER_PHONE_DAO.get(userID);
        } catch (SQLException e) {
            rollback();
            throw new InternalErrorException("Oops, something went wrong while fetching Phones", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
