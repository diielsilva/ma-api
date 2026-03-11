package com.todev.ma.crypto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
class EncodingConfiguration {
    @Value(value = "${crypto.secret}")
    private String secret;

    @Value(value = "${crypto.standard}")
    private String standard;

    @Value(value = "${crypto.algorithm}")
    private String algorithm;

    String getSecret() {
        return secret;
    }

    String getStandard() {
        return standard;
    }

    String getAlgorithm() {
        return algorithm;
    }
}
