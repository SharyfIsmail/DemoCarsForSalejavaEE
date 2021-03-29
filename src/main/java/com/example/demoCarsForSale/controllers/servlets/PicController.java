package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.controllers.utils.UriUtil;
import com.example.demoCarsForSale.services.PicHandler;
import com.example.demoCarsForSale.services.impl.PicService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "picController", urlPatterns = "/api/v1/pics/*")
public class PicController extends BaseController {
    private static final PicHandler PIC_HANDLER = new PicService();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        long id = UriUtil.getIdFromPath(request.getPathInfo());
        UserResponse attribute = (UserResponse) request.getSession().getAttribute("user");

        executeWithNoResult(() -> PIC_HANDLER.delete(id, attribute.getUserId()));
    }
}
