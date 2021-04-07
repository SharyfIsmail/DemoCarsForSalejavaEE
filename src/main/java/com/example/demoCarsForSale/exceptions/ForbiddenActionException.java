package com.example.demoCarsForSale.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenActionException extends ApiBaseException {

    public ForbiddenActionException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
