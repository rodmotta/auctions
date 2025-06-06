package com.github.rodmotta.auth_service.controller;

import com.github.rodmotta.auth_service.dto.request.RefreshTokenRequest;
import com.github.rodmotta.auth_service.dto.request.TokenRequest;
import com.github.rodmotta.auth_service.dto.response.TokenResponse;
import com.github.rodmotta.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("token")
    @ResponseStatus(OK)
    public TokenResponse exchangeCode(@RequestBody @Valid TokenRequest tokenRequest) {
        return authService.exchangeCode(tokenRequest);
    }

    @PostMapping("refresh-token")
    @ResponseStatus(OK)
    public TokenResponse refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("logout")
    @ResponseStatus(OK)
    public void logout(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        authService.logout(refreshTokenRequest);
    }
}
