package com.github.rodmotta.auth_service.service;

import com.github.rodmotta.auth_service.client.KeycloakClient;
import com.github.rodmotta.auth_service.dto.request.RefreshTokenRequest;
import com.github.rodmotta.auth_service.dto.request.TokenRequest;
import com.github.rodmotta.auth_service.dto.response.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final KeycloakClient keycloakClient;

    public AuthService(KeycloakClient keycloakClient) {
        this.keycloakClient = keycloakClient;
    }

    public TokenResponse exchangeCode(TokenRequest tokenRequest) {
        logger.info("Attempting to exchange authorization code for tokens");
        var keycloakTokenResponse = keycloakClient.exchangeCode(tokenRequest.code(), tokenRequest.redirectUri());
        logger.info("Successfully exchanged authorization code. Received access token");
        return new TokenResponse(keycloakTokenResponse);
    }

    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        logger.info("Attempting to refresh access token using refresh token");
        var keycloakTokenResponse = keycloakClient.refreshToken(refreshTokenRequest.refreshToken());
        logger.info("Successfully refreshed access token.");
        return new TokenResponse(keycloakTokenResponse);
    }

    public void logout(RefreshTokenRequest refreshTokenRequest) {
        logger.info("Attempting to log out user using refresh token");
        keycloakClient.logout(refreshTokenRequest.refreshToken());
        logger.info("Successfully logged out user.");
    }
}
