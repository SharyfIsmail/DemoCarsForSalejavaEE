package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.AdDao;
import com.example.demoCarsForSale.dao.db.ConnectionManager;
import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Condition;
import com.example.demoCarsForSale.exeptions.EntityNotFoundException;
import com.example.demoCarsForSale.exeptions.ForbiddenActionException;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AdDaoImpl extends AbstractDao implements AdDao {
    private static final String CREATE_AD = "INSERT INTO ADS (USER_ID, YEAR, BRAND, MODEL, ENGINE_CAPACITY, CONDITION," +
        "MILEAGE, POWER, CREATING_DATE, EDIT_DATE) VALUES (?, ?, ?, ?, ?, CAST(? AS CURRENT_CONDITION), ?, ?, ?, ?)";
    private static final String GET_AD = "SELECT * FROM ADS WHERE AD_ID = ?";
    private static final String GET_ALL_USER_ADS = "SELECT * FROM ADS WHERE USER_ID = ?";
    private static final String DELETE_AD = "DELETE FROM ADS WHERE AD_ID = ? AND USER_ID = ?";
    private static final String UPDATE_AD = "UPDATE ADS SET YEAR = ?, BRAND = ?, MODEL = ?, ENGINE_CAPACITY = ?," +
        "MILEAGE = ?, POWER = ?, EDIT_DATE = ? WHERE ID = ?";
    private static final String UPDATE_EDIT_DATE_FROM_AD = "UPDATE ADS SET EDIT_DATE = ? WHERE AD_ID = ?";

    @Override
    public Ad save(Ad model) throws SQLException {
        try (PreparedStatement preparedStatement = preparedStatement(CREATE_AD, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, model.getUserId());
            preparedStatement.setInt(2, model.getYear());
            preparedStatement.setString(3, model.getBrand());
            preparedStatement.setString(4, model.getModel());
            preparedStatement.setInt(5, model.getEngineCapacity());
            preparedStatement.setObject(6, model.getCondition(), Types.OTHER);
            preparedStatement.setInt(7, model.getMileage());
            preparedStatement.setInt(8, model.getPower());
            preparedStatement.setTimestamp(9, model.getCreateDate());
            preparedStatement.setTimestamp(10, model.getEditDate());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                model.setAdId(resultSet.getLong(1));
            }
        }

        return model;
    }

    @Override
    public Ad get(Serializable id) throws SQLException {
        Ad ad;

        try (PreparedStatement preparedStatement = preparedStatement(GET_AD, Statement.NO_GENERATED_KEYS)) {
            preparedStatement.setLong(1, (long) id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ad = getAd(resultSet);
            } else {
                throw new EntityNotFoundException("No such ad :", HttpServletResponse.SC_NOT_FOUND);
            }
        }

        return ad;
    }

    @Override
    public List<Ad> getAllUserAds(long userId) throws SQLException {
        List<Ad> ads = new ArrayList<>();

        try (PreparedStatement preparedStatement = preparedStatement(GET_ALL_USER_ADS, Statement.NO_GENERATED_KEYS)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                do {
                    ads.add(getAd(resultSet));
                } while (resultSet.next());
            } else {
                throw new EntityNotFoundException("this user has no ads:", HttpServletResponse.SC_NOT_FOUND);
            }
        }
        return ads;
    }

    @Override
    public List<Ad> getRecords(int page, int total) throws SQLException {
        List<Ad> ads = new ArrayList<>();

        try (PreparedStatement preparedStatement = ConnectionManager
            .getConnection()
            .prepareStatement("SELECT * FROM ADS limit ? OFFSET ?")) {

            preparedStatement.setInt(1, page);
            preparedStatement.setInt(2, total);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ads.add(Ad.builder()
                    .adId(resultSet.getLong("AD_ID"))
                    .year(resultSet.getInt("YEAR"))
                    .brand(resultSet.getString("BRAND"))
                    .model(resultSet.getString("MODEL"))
                    .condition(Condition.valueOf(resultSet.getString("CONDITIOM")))
                    .createDate(resultSet.getTimestamp("CREATE_DATE"))
                    .build());
            }
        }
        return ads;
    }

    @Override
    public void updateEditDate(long adId) throws SQLException {
        try (PreparedStatement preparedStatement = preparedStatement(UPDATE_EDIT_DATE_FROM_AD, Statement.NO_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(2, adId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Ad object) throws SQLException {
        try (PreparedStatement preparedStatement = preparedStatement(DELETE_AD, Statement.NO_GENERATED_KEYS)) {
            preparedStatement.setLong(1, object.getAdId());
            preparedStatement.setLong(2, object.getUserId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new ForbiddenActionException("Forbidden action", HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }

    private static Ad getAd(ResultSet resultSet) throws SQLException {
        return Ad.builder()
            .adId(resultSet.getLong("AD_ID"))
            .year(resultSet.getInt("YEAR"))
            .brand(resultSet.getString("BRAND"))
            .model(resultSet.getString("MODEL"))
            .engineCapacity(resultSet.getInt("ENGINE_CAPACITY"))
            .condition(Condition.valueOf(resultSet.getString("CONDITION")))
            .mileage(resultSet.getInt("MILEAGE"))
            .power(resultSet.getInt("POWER"))
            .createDate(resultSet.getTimestamp("CREATING_DATE"))
            .editDate(resultSet.getTimestamp("EDIT_DATE"))
            .build();
    }
}
