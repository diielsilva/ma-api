package com.todev.ma.security;

import com.auth0.jwt.JWT;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
class TokenService {
    private final TokenConfiguration tokenConfiguration;

    TokenService(TokenConfiguration tokenConfiguration) {
        this.tokenConfiguration = tokenConfiguration;
    }

    String create(String subject) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + tokenConfiguration.getExpiration());
        String issuer = "ma-api";

        return JWT
                .create()
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withSubject(subject)
                .withExpiresAt(expiresAt)
                .sign(HMAC512(tokenConfiguration.getSecret()));
    }

    String validate(String token) {
        token = token.replace("Bearer ", "");

        return JWT
                .require(HMAC512(tokenConfiguration.getSecret()))
                .build()
                .verify(token)
                .getSubject();
    }
}
