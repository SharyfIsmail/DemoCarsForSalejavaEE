package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.services.UserHandler;
import com.example.demoCarsForSale.services.impl.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserController", urlPatterns = "/api/v1/users")
public class UserController extends BaseController {
    private static final UserHandler USER_HANDLER = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        executeWithResult(response, USER_HANDLER::findUserExtraInfo);
    }
}
