package com.example.demoCarsForSale.services;

@FunctionalInterface
public interface PicHandler {

    void delete(long id, long userId);

    default boolean isAbleToDelete(long permissionId, long userId) {
        return permissionId == userId;
    }
}
