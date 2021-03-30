package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.controllers.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.services.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SignUpController", urlPatterns = "/api/v1/signup")
public class SignUpController extends BaseController {
    private static final UserService USER_SIGN_UP_HANDLER = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        UserSignUpRequest userSignUpRequest = getRequestObject(request, UserSignUpRequest.class);

        executeWithResult(response, () -> {
            UserResponse user = USER_SIGN_UP_HANDLER.createUser(userSignUpRequest);
            request.getSession(true).setAttribute("user", user);

            return user;
        });
    }
}
