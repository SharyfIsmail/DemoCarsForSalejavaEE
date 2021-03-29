package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.controllers.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.services.UserUpdateHandler;
import com.example.demoCarsForSale.services.impl.UserUpdateService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateController", urlPatterns = "/api/v1/updateuser")
public class UpdateUserController extends BaseController {
    private static final UserUpdateHandler USER_UPDATE_HANDLER = new UserUpdateService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        UserUpdateRequest userUpdateRequest = getRequestObject(request, UserUpdateRequest.class);
        UserResponse attribute = (UserResponse) request.getSession().getAttribute("user");

        executeWithNoResult(() ->
            USER_UPDATE_HANDLER.updateUser(userUpdateRequest, attribute.getUserId()));
    }
}


