package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.PicDao;
import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Pic;
import com.example.demoCarsForSale.exeptions.ForbiddenActionException;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PicDaoImpl extends AbstractDao implements PicDao {
    private static final String CREATE_PIC = "INSERT INTO PICS(AD_ID, PIC) VALUES (?,?)";
    private static final String GET_PICS = "SELECT PIC FROM PICS WHERE AD_ID = ?";
    private static final String SELECT_PIC_TO_DELETE = " SELECT  PIC_ID FROM PICS INNER JOIN ADS ON ADS.USER_ID = ? WHERE ADS.AD_ID = ?";
    private static final String DELETE_FIRST_PIC = "DELETE FROM PICS WHERE PIC_ID = ?";

    @Override
    public List<Pic> save(List<Pic> pics) throws SQLException {
        try (PreparedStatement preparedStatement = preparedStatement(CREATE_PIC, Statement.NO_GENERATED_KEYS)) {
            for (Pic pic : pics) {
                preparedStatement.setLong(1, pic.getAdId());
                preparedStatement.setString(2, pic.getCarPic());
                preparedStatement.addBatch();
            }

            preparedStatement.executeUpdate();
        }

        return pics;
    }

    @Override
    public List<Pic> get(Serializable adId) throws SQLException {
        List<Pic> pics = new ArrayList<>();
        try (PreparedStatement preparedStatement = preparedStatement(GET_PICS, Statement.NO_GENERATED_KEYS)) {
            preparedStatement.setLong(1, (long) adId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                do {
                    pics.add(Pic.builder()
                        .carPic(resultSet.getString("PIC"))
                        .build());
                } while (resultSet.next());
            }
        }
        return pics;
    }

    @Override
    public void delete(Ad ad) throws SQLException {
        try (PreparedStatement preparedStatement = preparedStatement(SELECT_PIC_TO_DELETE, Statement.NO_GENERATED_KEYS)) {
            preparedStatement.setLong(1, ad.getUserId());
            preparedStatement.setLong(2, ad.getAdId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long picId = resultSet.getLong("PIC_ID");

                try (PreparedStatement preparedStatement1 = preparedStatement(DELETE_FIRST_PIC, Statement.NO_GENERATED_KEYS)) {
                    preparedStatement1.setLong(1, picId);
                    preparedStatement1.executeUpdate();
                }
            } else {
                throw new ForbiddenActionException("Forbidden action", HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }
}

