package com.example.demoCarsForSale.exeptions;

public class EntityNotFoundException extends AbstractThrowableException {
    public EntityNotFoundException(String message, int errorStatus) {
        super(message, errorStatus);
    }

    public EntityNotFoundException(String message, Throwable e, int errorStatus) {
        super(message, e, errorStatus);
    }
}
