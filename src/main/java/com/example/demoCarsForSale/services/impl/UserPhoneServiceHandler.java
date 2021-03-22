package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.UserPhoneDao;
import com.example.demoCarsForSale.dao.impl.UserPhoneDaoImpl;
import com.example.demoCarsForSale.dao.model.UserPhone;
import com.example.demoCarsForSale.exeptions.BadRequestException;
import com.example.demoCarsForSale.exeptions.InternalErrorException;
import com.example.demoCarsForSale.services.UserPhoneService;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class UserPhoneServiceHandler extends AbstractService implements UserPhoneService {
    private static final UserPhoneDao USER_PHONE_DAO = new UserPhoneDaoImpl();

    @Override
    public List<UserPhone> save(List<UserPhone> phones) {
        try {
            List<UserPhone> userPhones = USER_PHONE_DAO.save(phones);
            commit();

            return userPhones;
        } catch (SQLException e) {
            rollback();
            throw new BadRequestException(e.getMessage(), e, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public List<UserPhone> get(Object userID) {
        try {
            List<UserPhone> userPhones = USER_PHONE_DAO.get(userID);
            commit();

            return userPhones;
        } catch (SQLException e) {
            rollback();
            throw new InternalErrorException("Oops, something went wrong while fetching Phones", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
