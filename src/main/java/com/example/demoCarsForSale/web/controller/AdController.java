package com.example.demoCarsForSale.web.controller;

import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.web.SecurityContextPrincipalSupplier;
import com.example.demoCarsForSale.web.dto.request.AdRequest;
import com.example.demoCarsForSale.web.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.web.dto.response.AdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
public class AdController extends SecurityContextPrincipalSupplier {
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_RECORDS_TO_SHOW = "10";
    private final AdService adService;

    @GetMapping("/{id}")
    public ResponseEntity<AdDetailedResponse> read(@PathVariable long id) {
        return new ResponseEntity<>(adService.getDetailedInfoAboutAd(id), HttpStatus.OK);
    }

    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdDetailedResponse> create(@Valid @RequestBody AdRequest adRequest) {
        return new ResponseEntity<>(adService.createAd(adRequest, getUser().getUserId()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        adService.deleteAd(id, getUser().getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<AdResponse>> readAll(
        @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(value = "size", defaultValue = DEFAULT_RECORDS_TO_SHOW) int size) {

        return new ResponseEntity<>(adService.getRecords(PageRequest.of(page, size, Sort.by("createDate"))), HttpStatus.OK);
    }
}
