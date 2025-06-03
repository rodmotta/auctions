package com.github.rodmotta.user_service.controller;

import com.github.rodmotta.user_service.dto.request.LoginRequest;
import com.github.rodmotta.user_service.dto.request.RegisterRequest;
import com.github.rodmotta.user_service.dto.response.JwtResponse;
import com.github.rodmotta.user_service.dto.response.UserResponse;
import com.github.rodmotta.user_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("users")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    @ResponseStatus(CREATED)
    public void register(@RequestBody @Valid RegisterRequest registerRequest) {
        authService.register(registerRequest);
    }

    @PostMapping("login")
    @ResponseStatus(OK)
    public JwtResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("{userId}")
    @ResponseStatus(OK)
    public UserResponse getUserById(@PathVariable Long userId) {
        return authService.getUserById(userId);
    }
}
