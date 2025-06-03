package com.github.rodmotta.user_service.request;

public record RegisterRequest(String email, String password, String name) {
}
