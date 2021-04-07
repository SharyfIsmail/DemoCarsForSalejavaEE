package com.example.demoCarsForSale.web.controller;

import com.example.demoCarsForSale.exceptions.ApiBaseException;
import com.example.demoCarsForSale.web.dto.response.ErrorResponse;
import com.example.demoCarsForSale.web.dto.response.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(ApiBaseException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiBaseException exception, WebRequest request) {
        return new ResponseEntity<>(ErrorResponse.builder()
            .error(exception.getMessage())
            .uri(request.getDescription(false))
            .build(), exception.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        ValidationErrorResponse validationError = ValidationErrorResponse.builder().build();
        validationError.setUri(request.getDescription(false));

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        fieldErrors.forEach(fieldError -> validationError.addError(fieldError.getDefaultMessage()));

        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }
}
