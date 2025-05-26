package com.github.rodmotta.user_service.controller;

import com.github.rodmotta.user_service.dto.request.LoginRequest;
import com.github.rodmotta.user_service.dto.request.RegisterRequest;
import com.github.rodmotta.user_service.dto.response.JwtResponse;
import com.github.rodmotta.user_service.service.AuthService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("users")
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    @ResponseStatus(CREATED)
    public void register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
    }

    @PostMapping("login")
    @ResponseStatus(OK)
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
