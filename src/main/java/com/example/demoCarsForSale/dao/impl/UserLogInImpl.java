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
import java.sql.Statement;

public class UserLogInImpl extends AbstractDao implements UserDao {
    private static final String GET_USER = "SELECT * FROM USERS WHERE EMAIL = ?";
    private static final String CREATE_USER = "INSERT INTO USERS (USER_NAME, EMAIL, PASSWORD) VALUES(?,?,?)";

    @Override
    public User save(User model) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionManager.getConnection()
            .prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getEmail());
            preparedStatement.setString(3, model.getPassword());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                model.setId(resultSet.getLong(1));
            }
        }

        return model;
    }

    @Override
    public User get(Serializable object) throws SQLException {
        User user;
        try (PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(GET_USER)) {
            preparedStatement.setString(1, (String) object);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = User.builder()
                    .id(resultSet.getLong("USER_ID"))
                    .name(resultSet.getString("USER_NAME"))
                    .email(resultSet.getString("EMAIL"))
                    .password(resultSet.getString("PASSWORD"))
                    .build();
            } else {
                throw new EntityNotFoundException("Wrong email, please type it correctly", HttpServletResponse.SC_NOT_FOUND);
            }
        }
        return user;
    }
}
