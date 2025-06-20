package com.github.rodmotta.auth_service.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KeycloakTokenResponse(
        String accessToken,
        int expiresIn,
        String refreshToken,
        int refreshExpiresIn
) {
}
