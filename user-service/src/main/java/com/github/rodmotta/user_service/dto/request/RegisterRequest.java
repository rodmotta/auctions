package com.github.rodmotta.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Min(5)
        String password,
        @NotBlank
        String name
) {
}
