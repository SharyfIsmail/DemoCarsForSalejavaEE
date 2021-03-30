package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.controllers.utils.UriUtil;
import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.services.impl.AdServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdController", urlPatterns = "/api/v1/ads/*")
public class AdController extends BaseController {
    private static final AdService AD_HANDLER = new AdServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        executeWithResult(response, () ->
            AD_HANDLER.getDetailedInfoAboutAd(UriUtil.getIdFromPath(request.getPathInfo())));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        long id = UriUtil.getIdFromPath(request.getPathInfo());

        UserResponse attribute = (UserResponse) request.getSession().getAttribute("user");

        executeWithNoResult(() -> AD_HANDLER.deleteAd(id, attribute.getUserId()));
    }
}
