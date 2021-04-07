package com.example.demoCarsForSale.web.controller;

import com.example.demoCarsForSale.services.PicService;
import com.example.demoCarsForSale.web.SecurityContextPrincipalSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pics")
@RequiredArgsConstructor
public class PicController extends SecurityContextPrincipalSupplier {
    private final PicService picService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePic(@PathVariable long id) {
        picService.delete(id, getUser().getUserId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
