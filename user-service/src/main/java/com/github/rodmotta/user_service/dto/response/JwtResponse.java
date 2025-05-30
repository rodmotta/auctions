package com.github.rodmotta.user_service.dto.response;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public record JwtResponse(
        String accessToken,
        LocalDateTime expiresAt
) {
    public JwtResponse(String accessToken, Date expiration) {
        this(
                accessToken,
                expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        );
    }
}
