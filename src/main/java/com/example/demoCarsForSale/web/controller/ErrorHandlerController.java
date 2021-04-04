package com.example.demoCarsForSale.web.controller;

import com.example.demoCarsForSale.exceptions.AbstractThrowableException;
import lombok.Builder;
import lombok.Getter;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletErrorHandlerController", urlPatterns = "/errorHandler")
public class ErrorHandlerController extends BaseController {
    private static final Logger LOG = Logger.getLogger(ErrorHandlerController.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (throwable instanceof AbstractThrowableException) {
            response.setStatus(((AbstractThrowableException) throwable).getErrorStatus());
        }

        executeWithResult(response, () -> ErrorApiResponse.builder()
            .errorClass(null)
            .errorMessage(throwable.getMessage())
            .build());

        LOG.error(throwable.getClass() + ":" + throwable.getMessage());
    }

    @Builder
    @Getter
    static class ErrorApiResponse {
        private final String errorMessage;
        private final String errorClass;
    }
}
