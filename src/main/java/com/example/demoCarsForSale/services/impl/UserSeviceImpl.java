package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.controllers.dto.UserDto;
import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.impl.UserDaoImpl;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.exeptions.InternalErrorException;
import com.example.demoCarsForSale.mapper.UserMapper;
import com.example.demoCarsForSale.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class UserSeviceImpl extends AbstractService implements UserService {
    private static final UserDao USER_DAO = new UserDaoImpl();

    @Override
    public UserDto save(UserDto userDto) {
        UserDto userDto1;
        try {
            User user = USER_DAO.save(UserMapper.convertFromDto(userDto));
            userDto1 = UserMapper.convertToDto(user);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new InternalErrorException("Internal error", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return userDto1;
    }

    @Override
    public UserDto get(long id) {
        UserDto userDto;

        try {
            User user = USER_DAO.get(id);
            userDto = UserMapper.convertToDto(user);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new InternalErrorException("Internal error", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return userDto;
    }

    @Override
    public void update(UserDto userDto) {
        try {
            USER_DAO.update(UserMapper.convertFromDto(userDto));
            commit();
        } catch (SQLException e) {
            rollback();
            throw new InternalErrorException("Internal error", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(long id) {
        try {
            USER_DAO.delete(id);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new InternalErrorException("Internal error", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
