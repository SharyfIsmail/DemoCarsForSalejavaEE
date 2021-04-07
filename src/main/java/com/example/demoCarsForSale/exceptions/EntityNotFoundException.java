package com.example.demoCarsForSale.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ApiBaseException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
