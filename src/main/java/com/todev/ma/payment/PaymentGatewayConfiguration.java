package com.todev.ma.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class PaymentGatewayConfiguration {
    @Value(value = "${payment.gateway.url}")
    private String url;

    @Value(value = "${payment.gateway.token}")
    private String token;

    @Value(value = "${payment.gateway.webhook}")
    private String webhook;

    @Bean
    protected RestClient getRestClient() {
        return RestClient.builder().baseUrl(getUrl()).build();
    }

    String getUrl() {
        return url;
    }

    String getToken() {
        return token;
    }

    String getWebhook() {
        return webhook;
    }
}
