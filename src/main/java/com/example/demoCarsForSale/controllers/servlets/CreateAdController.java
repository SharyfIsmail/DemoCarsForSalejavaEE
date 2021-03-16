package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.controllers.dto.request.AdRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.services.impl.AdServiceHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdCreatorController", urlPatterns = "/api/v1/createAd")
public class CreateAdController extends BaseController {
    private static final AdService AD_SERVICE = new AdServiceHandler();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        UserResponse User = (UserResponse) request.getSession().getAttribute("user");
        executeWithResult(response, () -> AD_SERVICE.save(getRequestObject(request, AdRequest.class), User.getUserId()));
    }
}
