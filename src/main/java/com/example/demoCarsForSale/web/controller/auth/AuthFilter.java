package com.example.demoCarsForSale.web.controller.auth;

import com.example.demoCarsForSale.pojo.User;
import com.example.demoCarsForSale.security.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component("authFilter")
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private final UserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNotEmpty(header) && header.startsWith(BEARER_PREFIX)) {
            String token = header.substring(BEARER_PREFIX.length());
            String email = TokenUtil.getEmailFromToken(token);

            if (email != null) {
                User user = (User) userService.loadUserByUsername(email);

                if (TokenUtil.isTokenValid(token, user)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                        null,
                        Collections.unmodifiableList(AuthorityUtils.NO_AUTHORITIES));

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
