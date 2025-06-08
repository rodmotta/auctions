package com.github.rodmotta.auction_service.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String message,
        Map<String, String> validations
) {
    public ErrorResponse(String message) {
        this(message, null);
    }
}
