package com.example.demoCarsForSale.web.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LogOutController", urlPatterns = "/api/v1/logout")
public class LogOutController extends BaseController {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        request.getSession(false).invalidate();
    }
}
