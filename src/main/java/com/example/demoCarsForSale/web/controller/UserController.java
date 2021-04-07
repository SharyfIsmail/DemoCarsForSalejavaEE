package com.example.demoCarsForSale.web.controller;

import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.web.SecurityContextPrincipalSupplier;
import com.example.demoCarsForSale.web.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.web.dto.response.UserExtraInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController extends SecurityContextPrincipalSupplier {
    private final UserService userService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<UserExtraInfoResponse>> findUsers() {
        return new ResponseEntity<>(userService.findUsersExtraInfo(), HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserUpdateRequest request) {
        userService.updateUser(request, getUser().getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
