package com.example.demoCarsForSale.exeptions;

public class ForbiddenActionException extends AbstractThrowableException {
    public ForbiddenActionException(String message, Throwable e, int errorStatus) {
        super(message, e, errorStatus);
    }

    public ForbiddenActionException(String message, int errorStatus) {
        super(message, errorStatus);
    }
}
