package com.todev.ma.security;

import com.todev.ma.order.payment.PaymentGatewayConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration {
    @Bean
    protected PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain getSecurityFilterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager,
            UserDetailsRepository userDetailsRepository,
            TokenService tokenService,
            PaymentGatewayConfiguration paymentGatewayConfiguration
    ) {
        TokenFilter tokenFilter = new TokenFilter(authenticationManager, userDetailsRepository, tokenService);

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(configurer -> configurer.configurationSource(request -> getAllowedCors()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestAuthorizer ->
                        requestAuthorizer
                                .requestMatchers(POST, "/users").permitAll()
                                .requestMatchers(POST, "/login").permitAll()
                                .requestMatchers(POST, "/enrollments").permitAll()
                                .anyRequest()
                                .authenticated())
                .addFilterBefore(tokenFilter, BasicAuthenticationFilter.class)
                .build();
    }

    private CorsConfiguration getAllowedCors() {
        CorsConfiguration cors = new CorsConfiguration();

        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
        cors.setAllowCredentials(true);
        cors.setAllowedHeaders(List.of("*"));
        cors.setAllowedOriginPatterns(List.of("http://localhost:4200", "https://app.movimenta.site"));

        return cors;
    }

}
