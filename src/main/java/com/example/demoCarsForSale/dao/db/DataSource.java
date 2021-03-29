package com.example.demoCarsForSale.dao.db;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataSource {

    public static EntityManager getConnection() {
        return Persistence.createEntityManagerFactory("myPersistence").createEntityManager();
    }
}
