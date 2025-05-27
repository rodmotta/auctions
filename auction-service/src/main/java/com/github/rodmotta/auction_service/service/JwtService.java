package com.github.rodmotta.auction_service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class JwtService {

    //@Value("jwt.secret-key")
    private String secretKey = "test"; //todo - criar variavel de ambiente

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
            return true;
        } catch (UnsupportedEncodingException | JWTVerificationException e) {
            return false;
        }
    }

    public String getSubject(String token) {
        return JWT.decode(token)
                .getSubject();
    }
}
