package com.example.demoCarsForSale.web.controller.auth;

import com.example.demoCarsForSale.web.dto.response.UserResponse;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(servletNames = {"AdCreatorController",
    "AdController", "picController", "LogOutController", "UpdateController"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        UserResponse userLogInResponse = (UserResponse) request.getSession().getAttribute("user");

        if (userLogInResponse != null) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("You first need to log in or sign up");
        }
    }
}
