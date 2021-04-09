package com.example.demoCarsForSale.web.controller;

import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.pojo.User;
import com.example.demoCarsForSale.security.TokenUtil;
import com.example.demoCarsForSale.services.impl.UserServiceImpl;
import com.example.demoCarsForSale.web.SecurityContextPrincipalSupplier;
import com.example.demoCarsForSale.web.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.web.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.web.dto.response.JwtResponse;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends SecurityContextPrincipalSupplier {
    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> logIn(@Valid @RequestBody UserLogInRequest userLogInRequest) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogInRequest.getUserEmail(),
                    userLogInRequest.getUserPassword()));
        } catch (RuntimeException e) {
            throw new BadRequestException("Wrong email or password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) userService.loadUserByUsername(userLogInRequest.getUserEmail());
        String token = TokenUtil.generateToken(user);

        return new ResponseEntity<>(JwtResponse.builder()
            .token(token)
            .build(), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        return new ResponseEntity<>(userService.createUser(userSignUpRequest), HttpStatus.OK);
    }
}
