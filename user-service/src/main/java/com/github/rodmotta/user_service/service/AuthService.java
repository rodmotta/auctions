package com.github.rodmotta.user_service.service;

import com.github.rodmotta.user_service.dto.request.LoginRequest;
import com.github.rodmotta.user_service.dto.request.RegisterRequest;
import com.github.rodmotta.user_service.dto.response.JwtResponse;
import com.github.rodmotta.user_service.dto.response.UserResponse;
import com.github.rodmotta.user_service.entity.UserEntity;
import com.github.rodmotta.user_service.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
        var user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtService.generate(user.getId());
    }

    @Cacheable
    public UserResponse getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(UserResponse::new)
                .orElseThrow(RuntimeException::new);
    }
}
