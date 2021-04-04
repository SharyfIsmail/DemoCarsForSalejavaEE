package com.example.demoCarsForSale.web.controller;

import com.example.demoCarsForSale.web.dto.request.AdRequest;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.services.impl.AdServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdCreatorController", urlPatterns = "/api/v1/adcreate")
public class CreateAdController extends BaseController {
    private static final AdService AD_HANDLER = new AdServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        UserResponse user = (UserResponse) request.getSession().getAttribute("user");

        executeWithResult(response, () ->
            AD_HANDLER.createAd(getRequestObject(request, AdRequest.class), user.getUserId()));
    }
}
