package com.example.demoCarsForSale.exceptions;

public class InternalErrorException extends AbstractThrowableException {
    public InternalErrorException(String message, Throwable e, int errorStatus) {
        super(message, e, errorStatus);
    }

    public InternalErrorException(String message, int errorStatus) {
        super(message, errorStatus);
    }
}
