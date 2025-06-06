package com.github.rodmotta.auth_service.dto.response;

public record TokenResponse(
        String accessToken,
        int expiresIn,
        String refreshToken,
        int refreshExpiresIn
) {
    public TokenResponse(KeycloakTokenResponse keycloakTokenResponse) {
        this(
                keycloakTokenResponse.accessToken(),
                keycloakTokenResponse.expiresIn(),
                keycloakTokenResponse.refreshToken(),
                keycloakTokenResponse.refreshExpiresIn()
        );
    }
}
