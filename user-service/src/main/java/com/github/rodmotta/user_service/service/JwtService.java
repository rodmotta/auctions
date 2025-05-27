package com.github.rodmotta.user_service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JwtService {

    //@Value("jwt.secret-key")
    private String secretKey = "test"; //todo - criar variavel de ambiente

    public String generate(Long userId) {
        try {
            return JWT.create()
                    .withSubject(userId.toString())
                    .withIssuer("user-service")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) //10min
                    .sign(Algorithm.HMAC256(secretKey));
        } catch (UnsupportedEncodingException | JWTCreationException | IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
