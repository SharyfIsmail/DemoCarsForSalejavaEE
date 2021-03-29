package com.example.demoCarsForSale.exceptions;

public class BadRequestException extends AbstractThrowableException {
    public BadRequestException(String message, Throwable e, int errorStatus) {
        super(message, e, errorStatus);
    }

    public BadRequestException(String message, int errorStatus) {
        super(message, errorStatus);
    }
}
