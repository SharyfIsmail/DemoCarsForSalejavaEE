package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.db.ConnectionManager;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.exeptions.EntityNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends AbstractDao implements UserDao {
    private static final String CREATE_USER = "INSERT INTO USERS (USER_NAME, PASSWORD) VALUES(?,?)";
    private static final String DELETE_USER = "DELETE FROM USERS WHERE USER_ID = ?";
    private static final String GET_USER = "SELECT * FROM USERS WHERE USER_ID = ?";
    private static final String UPDATE_USER = "UPDATE USERS SET USER_NAME = ?, PASSWORD = ? WHERE USER_ID = ?";

    @Override
    public User save(User model) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(CREATE_USER)) {
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getPassword());
            preparedStatement.executeUpdate();
            model.setId(preparedStatement.getGeneratedKeys().getLong(1));
        }

        return model;
    }

    @Override
    public void delete(Serializable object) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, (long) object);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityNotFoundException("Entity is not found :", HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    public User get(Serializable object) throws SQLException {
        User user;

        try (PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(GET_USER)) {
            preparedStatement.setLong(1, (long) object);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                do {
                    user = User.builder()
                        .id(resultSet.getLong("USER_ID"))
                        .name(resultSet.getString("USER_NAME"))
                        .password(resultSet.getString("PASSWORD"))
                        .build();
                } while (resultSet.next());
            } else {
                throw new EntityNotFoundException("Entity is not fount :", HttpServletResponse.SC_NOT_FOUND);
            }
        }

        return user;
    }

    @Override
    public void update(User model) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getPassword());
            preparedStatement.setLong(3, model.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new EntityNotFoundException("Entity is not fount :", HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
