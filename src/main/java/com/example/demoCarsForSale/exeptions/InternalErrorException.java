package com.example.demoCarsForSale.exeptions;

public class InternalErrorException extends AbstractThrowableException {
    public InternalErrorException(String message, Throwable e, int errorStatus) {
        super(message, e, errorStatus);
    }

    public InternalErrorException(String message, int errorStatus) {
        super(message, errorStatus);
    }
}
