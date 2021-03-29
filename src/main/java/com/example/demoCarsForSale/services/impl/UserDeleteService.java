package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.impl.UserDaoImpl;
import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.services.UserDeleteHandler;

import javax.servlet.http.HttpServletResponse;

public class UserDeleteService extends AbstractService implements UserDeleteHandler {
    private static final UserDao USER_DELETE = new UserDaoImpl();

    @Override
    public void delete(long id) {
        try {
            startTransaction();
            USER_DELETE.delete(id);
            closeTransaction();
        } catch (Exception e) {
            rollback();

            throw new BadRequestException("Couldn't not delete user", e, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
