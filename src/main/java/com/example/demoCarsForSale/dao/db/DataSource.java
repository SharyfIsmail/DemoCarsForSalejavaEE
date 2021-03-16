package com.example.demoCarsForSale.dao.db;

import com.example.demoCarsForSale.exeptions.ConnectionFailedException;
import com.zaxxer.hikari.HikariDataSource;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public final class DataSource {
    private static final String URL;
    private static final String NAME;
    private static final String PASSWORD;
    private static final String DRIVER;
    private static final int MIN = 20;
    private static final int MAX = 100;

    private static final HikariDataSource HIKARI_DATA_SOURCE;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
        if (resourceBundle == null) {
            throw new ConnectionFailedException("Database resources were not found", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        URL = resourceBundle.getString("url");
        NAME = resourceBundle.getString("name");
        PASSWORD = resourceBundle.getString("password");
        DRIVER = resourceBundle.getString("driver");
        HIKARI_DATA_SOURCE = new HikariDataSource();

        HIKARI_DATA_SOURCE.setJdbcUrl(URL);
        HIKARI_DATA_SOURCE.setUsername(NAME);
        HIKARI_DATA_SOURCE.setPassword(PASSWORD);
        HIKARI_DATA_SOURCE.setDriverClassName(DRIVER);

        HIKARI_DATA_SOURCE.setAutoCommit(false);
        HIKARI_DATA_SOURCE.setMinimumIdle(MIN);
        HIKARI_DATA_SOURCE.setMaximumPoolSize(MAX);
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return HIKARI_DATA_SOURCE.getConnection();
    }
}
