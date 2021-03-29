package com.example.demoCarsForSale.exceptions;

public abstract class AbstractThrowableException extends RuntimeException {
    protected final int errorStatus;

    protected AbstractThrowableException(String message, Throwable e, int errorStatus) {
        super(message, e);
        this.errorStatus = errorStatus;
    }

    protected AbstractThrowableException(String message, int errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public int getErrorStatus() {
        return errorStatus;
    }
}
