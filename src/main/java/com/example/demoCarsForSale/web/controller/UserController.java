package com.example.demoCarsForSale.web.controller;

import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.services.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserController", urlPatterns = "/api/v1/users")
public class UserController extends BaseController {
    private static final UserService USER_FIND = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        executeWithResult(response, USER_FIND::findUserExtraInfo);
    }
}
