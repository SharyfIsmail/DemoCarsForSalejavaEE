package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.controllers.dto.UserDto;
import com.example.demoCarsForSale.controllers.utils.UriUtil;
import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.services.impl.UserSeviceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserController", urlPatterns = "/api/v1/user/*")
public class ServletUserController extends BaseController {
    private static final UserService USER_SERVICE = new UserSeviceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        executeWithResult(response, () ->
            USER_SERVICE.get(UriUtil.getIdFromPath(request.getPathInfo())));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        executeWithResult(response, () ->
            USER_SERVICE.save(getRequestObject(request, UserDto.class)));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        executeWithNoResult(() ->
            USER_SERVICE.update(getRequestObject(request, UserDto.class)));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        executeWithNoResult(() ->
            USER_SERVICE.delete(UriUtil.getIdFromPath(request.getPathInfo())));
    }
}
