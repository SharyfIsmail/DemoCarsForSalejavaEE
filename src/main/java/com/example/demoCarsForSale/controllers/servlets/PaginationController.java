package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.services.impl.AdServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PageController", urlPatterns = "/api/v1/ads")
public class PaginationController extends BaseController {
    private static final AdService AD_HANDLER = new AdServiceImpl();
    private static final int RECORDS_TO_SHOW = 10;
    private static final int DEFAULT_PAGE = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        executeWithResult(response, () -> {
            String stringValueOfPage = request.getParameter("page");
            int page = (stringValueOfPage == null || stringValueOfPage.isEmpty()) ? DEFAULT_PAGE : Integer.parseInt(stringValueOfPage);

            return AD_HANDLER.getRecords(page, RECORDS_TO_SHOW);
        });
    }
}
