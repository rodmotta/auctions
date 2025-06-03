package com.github.rodmotta.user_service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.github.rodmotta.user_service.dto.response.JwtResponse;
import com.github.rodmotta.user_service.entity.UserEntity;
import com.github.rodmotta.user_service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    public JwtResponse generate(UserEntity user) {
        try {
            Date date = new Date();
            Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10); //10min

            String accessToken = JWT.create()
                    .withSubject(user.getId().toString())
                    .withIssuer("user-service")
                    .withIssuedAt(date)
                    .withExpiresAt(expiration)
                    .withClaim("username", user.getName())
                    .sign(Algorithm.HMAC256(secretKey));

            return new JwtResponse(accessToken, expiration);
        } catch (UnsupportedEncodingException | JWTCreationException | IllegalArgumentException e) {
            throw new ValidationException(e.getMessage());
        }
    }
}
