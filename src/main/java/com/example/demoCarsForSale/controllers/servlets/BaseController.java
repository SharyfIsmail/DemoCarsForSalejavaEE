package com.example.demoCarsForSale.controllers.servlets;

import com.example.demoCarsForSale.exeptions.BadRequestException;
import com.example.demoCarsForSale.exeptions.InternalErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Supplier;

public abstract class BaseController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BaseController.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        if ("PATCH".equalsIgnoreCase(request.getMethod())) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    protected static void executeWithNoResult(Runnable runnable) {
        runnable.run();
    }

    protected static <T> void executeWithResult(HttpServletResponse response, Supplier<T> supplier) {
        response.setContentType("application/json");
        T result = supplier.get();

        try (PrintWriter printWriter = response.getWriter()) {
            String json = MAPPER.writeValueAsString(result);
            printWriter.write(json);
            LOGGER.info("Message is sent:" + json);
        } catch (IOException e) {
            throw new InternalErrorException("Sending Json error", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected static <T> T getRequestObject(HttpServletRequest request, Class<T> classType) {
        String message = getBody(request);

        try {
            LOGGER.info(message);
            return MAPPER.readValue(message, classType);
        } catch (IOException e) {
            throw new BadRequestException("Bad json format", e, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private static String getBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = request.getReader()) {
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new InternalErrorException("Receiving Json error", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return stringBuilder.toString();
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) {

    }
}
