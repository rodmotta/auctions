package com.github.rodmotta.bid_service.exception.handler;

import com.github.rodmotta.bid_service.exception.custom.ValidationException;
import com.github.rodmotta.bid_service.exception.dto.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse validationRequestExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> validations = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                validations.put(error.getField(), error.getDefaultMessage()));
        return new ErrorResponse("Validation error", validations);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponse badRequestExceptionHandler(RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse internalServerErrorExceptionHandler(Exception e) {
        log.error("An unexpected internal error occurred: {}", e.getMessage());
        return new ErrorResponse("An unexpected error occurred. Please try again later.");
    }
}
