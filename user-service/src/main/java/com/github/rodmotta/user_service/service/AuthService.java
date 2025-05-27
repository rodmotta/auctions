package com.github.rodmotta.user_service.service;

import com.github.rodmotta.user_service.dto.request.LoginRequest;
import com.github.rodmotta.user_service.dto.request.RegisterRequest;
import com.github.rodmotta.user_service.dto.response.JwtResponse;
import com.github.rodmotta.user_service.entity.UserEntity;
import com.github.rodmotta.user_service.repository.UserRepository;
import com.github.rodmotta.user_service.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterRequest registerRequest) {
        var user = userRepository.findByEmail(registerRequest.email());
        if (user.isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.password());
        var newUser = new UserEntity(registerRequest.email(), encodedPassword, registerRequest.name());
        userRepository.save(newUser);
    }

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        Authentication authenticated = authenticationManager.authenticate(auth);

        var authenticatedUser = (UserDetailsImpl) authenticated.getPrincipal();
        UserEntity user = authenticatedUser.userEntity();

        String accessToken = jwtService.generate(user.getId());
        return new JwtResponse(accessToken);
    }
}
