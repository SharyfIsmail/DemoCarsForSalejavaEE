package com.example.demoCarsForSale.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ApiBaseException extends RuntimeException {

    protected ApiBaseException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}
