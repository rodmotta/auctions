package com.github.rodmotta.auth_service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRequest(
        @NotBlank
        String code,
        @NotBlank
        String redirectUri
) {
}
