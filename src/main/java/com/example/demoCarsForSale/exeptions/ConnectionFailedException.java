package com.example.demoCarsForSale.exeptions;

public class ConnectionFailedException extends AbstractThrowableException {

    public ConnectionFailedException(String message, int errorCode) {
        super(message, errorCode);
    }

    public ConnectionFailedException(String message, Throwable e, int errorCode) {
        super(message, e, errorCode);
    }
}
