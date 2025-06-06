package com.github.rodmotta.auth_service.exception;

import com.github.rodmotta.auth_service.dto.response.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
}
