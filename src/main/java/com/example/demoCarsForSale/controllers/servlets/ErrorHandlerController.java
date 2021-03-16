package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.exeptions.AbstractThrowableException;
import lombok.Builder;
import lombok.Getter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletErrorHandlerController", urlPatterns = "/errorHandler")
public class ErrorHandlerController extends BaseController {

    @Builder
    @Getter
    static class ErrorApiResponse {
        private final String errorMessage;
        private final String errorClass;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (throwable instanceof AbstractThrowableException) {
            response.setStatus(((AbstractThrowableException) throwable).getErrorStatus());
        }

        executeWithResult(response, () -> ErrorApiResponse.builder()
            .errorClass(null)
            .errorMessage(throwable.getMessage())
            .build());
    }
}
