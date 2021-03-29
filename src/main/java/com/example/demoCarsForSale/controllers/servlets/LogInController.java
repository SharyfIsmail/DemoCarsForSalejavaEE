package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.controllers.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.services.UserLogInHandler;
import com.example.demoCarsForSale.services.impl.UserLoginService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LogInController", urlPatterns = "/api/v1/login")
public class LogInController extends BaseController {
    private static final UserLogInHandler USER_LOG_IN_HANDLER = new UserLoginService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        UserLogInRequest userLogInRequest = getRequestObject(request, UserLogInRequest.class);

        executeWithResult(response, () -> {
            UserResponse user = USER_LOG_IN_HANDLER.logIn(userLogInRequest);
            request.getSession(true)
                .setAttribute("user", user);

            return user;
        });
    }
}
