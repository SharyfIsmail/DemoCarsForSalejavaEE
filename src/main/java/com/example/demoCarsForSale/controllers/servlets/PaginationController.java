package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.services.impl.AdServiceHandler;
import com.example.demoCarsForSale.utils.Parser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PageController", urlPatterns = "/api/v1/page/*")
public class PaginationController extends BaseController {
    private static final AdService AD_SERVICE = new AdServiceHandler();
    private static final int TOTAL_PAGE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        executeWithResult(response, () -> {
            int page = (int) Parser.getIdFromPath(request.getPathInfo());
            page = page == 1 ? 1 : page * TOTAL_PAGE + 1;
            return AD_SERVICE.getRecords(page, TOTAL_PAGE);
        });
    }
}
