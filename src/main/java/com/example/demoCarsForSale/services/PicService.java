package com.example.demoCarsForSale.services;

import java.util.function.Supplier;

@FunctionalInterface
public interface PicService {

    void delete(long id, long userId);

    default boolean isAbleToDelete(long permissionId, long userId) {
        return permissionId == userId;
    }

    default void isValidAction(boolean isValid, Supplier<? extends RuntimeException> supplier) {
        if (!isValid) {
            supplier.get();
        }
    }
}
