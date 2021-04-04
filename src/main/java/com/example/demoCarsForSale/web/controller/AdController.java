package com.example.demoCarsForSale.web.controller;

import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.services.impl.AdServiceImpl;
import com.example.demoCarsForSale.web.dto.request.AdRequest;
import com.example.demoCarsForSale.web.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import com.example.demoCarsForSale.web.utils.UriUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/ads")
public class AdController extends BaseController {
    private static final AdService AD = new AdServiceImpl();

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdDetailedResponse> getAd(Long id) {
        AdDetailedResponse ad = AD.getDetailedInfoAboutAd(id);
        return new ResponseEntity<>(ad, HttpStatus.OK);
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<AdDetailedResponse> createAd(@RequestBody AdRequest adRequest) {
        AdDetailedResponse ad = AD.
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        executeWithResult(response, () ->
            AD_HANDLER.getDetailedInfoAboutAd(UriUtil.getIdFromPath(request.getPathInfo())));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        long id = UriUtil.getIdFromPath(request.getPathInfo());

        UserResponse attribute = (UserResponse) request.getSession().getAttribute("user");

        executeWithNoResult(() -> AD_HANDLER.deleteAd(id, attribute.getUserId()));
    }
}
