package com.example.demoCarsForSale.dao.db;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionManager {
    private static final ThreadLocal<EntityManager> THREAD_LOCAL = new ThreadLocal<>();

    public static EntityManager getConnection() {
        if (THREAD_LOCAL.get() == null) {
            THREAD_LOCAL.set(DataSource.getConnection());
        }
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        if (getConnection() != null) {
            THREAD_LOCAL.remove();
        }
    }
}
