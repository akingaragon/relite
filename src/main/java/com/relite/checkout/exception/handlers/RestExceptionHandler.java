package com.relite.checkout.exception.handlers;

import com.relite.checkout.exception.ApiError;
import com.relite.checkout.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ApiError handleIllegalArgumentException(IllegalArgumentException exception) {
        log.info(exception.getMessage(), exception);
        return new ApiError(1001, String.format("Illegal argument exception, please check your parameters (%s)", exception.getMessage()), Instant.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ItemNotFoundException.class)
    public ApiError handleIllegalArgumentException(ItemNotFoundException exception) {
        log.info(exception.getMessage(), exception);
        return new ApiError(1002, exception.getMessage(), Instant.now());
    }

}