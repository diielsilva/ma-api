package com.todev.ma.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
class TokenConfiguration {
    @Value(value = "${security.jwt.secret}")
    private String secret;

    @Value(value = "${security.jwt.expiration}")
    private Long expiration;

    String getSecret() {
        return secret;
    }

    Long getExpiration() {
        return expiration;
    }
}
