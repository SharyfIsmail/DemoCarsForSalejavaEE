package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.controllers.utils.UriUtil;
import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.services.impl.AdServiceHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PageController", urlPatterns = "/api/v1/page/*")
public class PaginationController extends BaseController {
    private static final AdService AD_SERVICE = new AdServiceHandler();
    private static final int RECORDS_TO_SHOW = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        executeWithResult(response, () -> {
            int page = (int) UriUtil.getIdFromPath(request.getPathInfo());
            int offset = page == 1 ? 0 : page * RECORDS_TO_SHOW;

            return AD_SERVICE.getRecords(RECORDS_TO_SHOW, offset);
        });
    }
}
