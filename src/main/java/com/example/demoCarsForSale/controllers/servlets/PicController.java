package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.services.PicService;
import com.example.demoCarsForSale.services.impl.PicServiceHandler;
import com.example.demoCarsForSale.controllers.utils.UriUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "picController", urlPatterns = "/api/v1/pic/*")
public class PicController extends BaseController {
    private static final PicService PIC_SERVICE = new PicServiceHandler();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        long id = UriUtil.getIdFromPath(request.getPathInfo());
        UserResponse user = (UserResponse) request.getSession().getAttribute("user");

        Ad ad = Ad.builder()
            .userId(user.getUserId())
            .adId(id)
            .build();

        executeWithNoResult(() -> PIC_SERVICE.delete(ad));
    }
}
