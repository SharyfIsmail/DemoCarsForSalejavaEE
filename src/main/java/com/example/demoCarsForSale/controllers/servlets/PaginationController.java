package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.services.AdHandler;
import com.example.demoCarsForSale.services.impl.AdService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PageController", urlPatterns = "/api/v1/ads")
public class PaginationController extends BaseController {
    private static final AdHandler AD_HANDLER = new AdService();
    private static final int RECORDS_TO_SHOW = 10;
    private static final int DEFAULT_PAGE = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        executeWithResult(response, () -> {
            String stringValueOfPage = request.getParameter("page");
            int page = (stringValueOfPage == null) ? DEFAULT_PAGE : Integer.parseInt(stringValueOfPage);

            return AD_HANDLER.getRecords(page, RECORDS_TO_SHOW);
        });
    }
}
