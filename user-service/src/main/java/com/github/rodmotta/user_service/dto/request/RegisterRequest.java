package com.github.rodmotta.user_service.dto.request;

public record RegisterRequest(String email, String password, String name) {
}
