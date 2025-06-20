package com.github.rodmotta.auth_service.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse validationRequestExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> validations = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                validations.put(error.getField(), error.getDefaultMessage()));
        return new ErrorResponse("Validation error", validations);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse internalServerErrorExceptionHandler(Exception e) {
        logger.error("An unexpected internal error occurred: {}", e.getMessage());
        return new ErrorResponse("An unexpected error occurred. Please try again later.");
    }
}
