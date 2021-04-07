package com.example.demoCarsForSale.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiBaseException {

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
