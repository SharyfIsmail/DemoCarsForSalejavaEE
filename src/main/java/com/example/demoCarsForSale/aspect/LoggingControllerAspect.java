package com.example.demoCarsForSale.aspect;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggingControllerAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingControllerAspect.class);

    @SneakyThrows
    @Around("execution(public * com.example.demoCarsForSale.web.controller.*.*(..))))")
    public static Object logAroundAllAdControllerMethods(ProceedingJoinPoint joinPoint) {
        LOGGER.info("{},request: {}",
            joinPoint.getSignature().toShortString(),
            Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .collect(Collectors.toList()));

        ResponseEntity responseEntity = (ResponseEntity) joinPoint.proceed();

        LOGGER.info("{} response: {}, HttpStatus : {}",
            joinPoint.getSignature().toShortString(),
            responseEntity.getBody(),
            responseEntity.getStatusCode());

        return responseEntity;
    }
}
