package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.controllers.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.services.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateController", urlPatterns = "/api/v1/updateuser")
public class UpdateUserController extends BaseController {
    private static final UserService USER_UPDATE_HANDLER = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        UserUpdateRequest userUpdateRequest = getRequestObject(request, UserUpdateRequest.class);
        UserResponse attribute = (UserResponse) request.getSession().getAttribute("user");

        executeWithNoResult(() ->
            USER_UPDATE_HANDLER.updateUser(userUpdateRequest, attribute.getUserId()));
    }
}


