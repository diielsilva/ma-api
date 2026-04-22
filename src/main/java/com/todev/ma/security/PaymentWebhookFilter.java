package com.todev.ma.security;

import com.todev.ma.order.payment.PaymentGatewayConfiguration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class PaymentWebhookFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(PaymentWebhookFilter.class);
    private final PaymentGatewayConfiguration paymentGatewayConfiguration;

    PaymentWebhookFilter(PaymentGatewayConfiguration paymentGatewayConfiguration) {
        this.paymentGatewayConfiguration = paymentGatewayConfiguration;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        try {
            boolean shouldValidateRequest = request.getServletPath().equals("/enrollments") && request.getMethod().equals("POST");


            if (shouldValidateRequest) {
                CacheableHttpServletRequest cachedRequest = new CacheableHttpServletRequest(request);

                if (wasRequestSentByPaymentGateway(cachedRequest)) {
                    filterChain.doFilter(cachedRequest, response);
                }

                return;
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private boolean wasRequestSentByPaymentGateway(HttpServletRequest request)
            throws IOException, NoSuchAlgorithmException {
        String paymentGatewaySignature = request.getHeader("x-authenticity-token");

        if (paymentGatewaySignature == null || paymentGatewaySignature.isEmpty()) {
            return false;
        }

        String signature = buildSignature(request);

        return signature.equals(paymentGatewaySignature);
    }

    private String buildSignature(HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        String paymentGatewayAccessToken = paymentGatewayConfiguration.getToken();
        String payload = new String(request.getInputStream().readAllBytes());
        String signature = paymentGatewayAccessToken + "-" + payload;

        return digest(signature);
    }

    private String digest(String signature) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedSignature = digest.digest(signature.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexStringConverter = new StringBuilder();

        for (byte b : encodedSignature) {
            String hexValue = Integer.toHexString(b & 0xFF);

            if (hexValue.length() == 1) {
                hexStringConverter.append('0');
            }

            hexStringConverter.append(hexValue);
        }

        return hexStringConverter.toString();
    }
}
