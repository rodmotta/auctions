package com.github.rodmotta.auth_service.service;

import com.github.rodmotta.auth_service.client.KeycloakClient;
import com.github.rodmotta.auth_service.dto.request.RefreshTokenRequest;
import com.github.rodmotta.auth_service.dto.request.TokenRequest;
import com.github.rodmotta.auth_service.dto.response.TokenResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final KeycloakClient keycloakClient;

    public AuthService(KeycloakClient keycloakClient) {
        this.keycloakClient = keycloakClient;
    }

    public TokenResponse exchangeCode(TokenRequest tokenRequest) {
        var keycloakTokenResponse = keycloakClient.exchangeCode(tokenRequest.code(), tokenRequest.redirectUri());
        return new TokenResponse(keycloakTokenResponse);
    }

    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        var keycloakTokenResponse = keycloakClient.refreshToken(refreshTokenRequest.refreshToken());
        return new TokenResponse(keycloakTokenResponse);
    }

    public void logout(RefreshTokenRequest refreshTokenRequest) {
        keycloakClient.logout(refreshTokenRequest.refreshToken());
    }
}
