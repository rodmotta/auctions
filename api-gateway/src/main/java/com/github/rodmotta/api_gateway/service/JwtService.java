package com.github.rodmotta.api_gateway.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

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

    public Map<String, Claim> getClaims(String token) {
        return JWT.decode(token)
                .getClaims();
    }
}
