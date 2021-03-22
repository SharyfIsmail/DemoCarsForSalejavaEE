package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.UserPhoneDao;
import com.example.demoCarsForSale.dao.model.UserPhone;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserPhoneDaoImpl extends AbstractDao implements UserPhoneDao {
    private static final String SAVE_PHONE = "INSERT INTO PHONES (USER_ID,PHONE) VALUES(?,?)";
    private static final String GET_PHONE = "SELECT * FROM PHONES WHERE USER_ID = ?";
    private static final String PHONE = "PHONE";

    @Override
    public List<UserPhone> save(List<UserPhone> userPhones) throws SQLException {
        try (PreparedStatement preparedStatement = preparedStatement(SAVE_PHONE, Statement.NO_GENERATED_KEYS)) {

            for (UserPhone userPhone : userPhones) {
                preparedStatement.setLong(1, userPhone.getUserId());
                preparedStatement.setString(2, userPhone.getPhone());
                preparedStatement.addBatch();
            }

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Make sure to type, at least, one phone number");
            }
        }

        return userPhones;
    }

    @Override
    public List<UserPhone> get(Object userId) throws SQLException {
        List<UserPhone> userPhones = new ArrayList<>();

        try (PreparedStatement preparedStatement = preparedStatement(GET_PHONE, Statement.NO_GENERATED_KEYS)) {
            preparedStatement.setLong(1, (long) userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                do {
                    userPhones.add(UserPhone.builder()
                        .phone(resultSet.getString(PHONE))
                        .build());
                } while (resultSet.next());
            }
        }

        return userPhones;
    }

    @Override
    public void update(List<UserPhone> model) throws SQLException {

    }

    @Override
    public void delete(Object id) throws SQLException {

    }
}
