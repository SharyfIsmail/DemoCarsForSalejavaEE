package com.example.demoCarsForSale.dao.db;

import com.example.demoCarsForSale.exeptions.ConnectionFailedException;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public final class ConnectionManager {
    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();

    private ConnectionManager() {
    }

    /**
     * @return Connection
     * @throws ConnectionFailedException runtimeException if connection fails
     */
    public static Connection getConnection() {
        try {
            if (THREAD_LOCAL.get() == null) {
                THREAD_LOCAL.set(DataSource.getConnection());
            }
        } catch (Exception e) {
            throw new ConnectionFailedException("Connection failed:", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return THREAD_LOCAL.get();
    }
}
