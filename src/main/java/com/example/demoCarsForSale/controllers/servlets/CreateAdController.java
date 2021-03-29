package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.controllers.dto.request.AdRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.services.AdHandler;
import com.example.demoCarsForSale.services.impl.AdService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdCreatorController", urlPatterns = "/api/v1/adcreate")
public class CreateAdController extends BaseController {
    private static final AdHandler AD_HANDLER = new AdService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        UserResponse user = (UserResponse) request.getSession().getAttribute("user");

        executeWithResult(response, () ->
            AD_HANDLER.createAd(getRequestObject(request, AdRequest.class), user.getUserId()));
    }
}
