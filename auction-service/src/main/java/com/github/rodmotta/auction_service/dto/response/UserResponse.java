package com.github.rodmotta.auction_service.dto.response;

import org.springframework.security.oauth2.jwt.Jwt;

public record UserResponse(
        String id,
        String name
) {
    public UserResponse(Jwt jwt) {
        this(
                jwt.getSubject(),
                jwt.getClaimAsString("name")
        );
    }
}
