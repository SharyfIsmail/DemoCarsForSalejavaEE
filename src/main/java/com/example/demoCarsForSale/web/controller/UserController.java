package com.example.demoCarsForSale.web.controller;

import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.web.SecurityContextPrincipalSupplier;
import com.example.demoCarsForSale.web.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.web.dto.response.UserExtraInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController extends SecurityContextPrincipalSupplier {
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_RECORDS_TO_SHOW = "10";
    private final UserService userService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<UserExtraInfoResponse>> findUsers(
        @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(value = "size", defaultValue = DEFAULT_RECORDS_TO_SHOW) int size) {

        return new ResponseEntity<>(userService.findUsersExtraInfo(PageRequest.of(page, size, Sort.by("ads"))), HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserUpdateRequest request) {
        userService.updateUser(request, getUser().getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> delete() {
        userService.delete(getUser().getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
