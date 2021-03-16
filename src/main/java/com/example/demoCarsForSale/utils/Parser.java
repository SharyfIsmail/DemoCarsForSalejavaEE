package com.example.demoCarsForSale.utils;

import com.example.demoCarsForSale.exeptions.BadRequestException;

import javax.servlet.http.HttpServletResponse;

public final class Parser {

    private Parser() {
    }

    public static long getIdFromPath(String url) {
        try {
            String sId = new StringBuffer(url).deleteCharAt(0).toString();
            return Long.parseLong(sId);
        } catch (NumberFormatException e) {
            throw new BadRequestException("The requested id must be a number", e, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
