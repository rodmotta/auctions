package com.github.rodmotta.auction_service.security;

import org.springframework.security.oauth2.jwt.Jwt;

public class JwtUserExtractor {

    public static User from(Jwt jwt) {
        return new User(
                jwt.getSubject(),
                jwt.getClaimAsString("name")
        );
    }
}
