package com.todev.ma.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

class TokenFilter extends BasicAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);
    private final UserDetailsRepository userDetailsRepository;
    private final TokenService tokenService;

    TokenFilter(
            AuthenticationManager authenticationManager,
            UserDetailsRepository userDetailsRepository,
            TokenService tokenService
    ) {
        super(authenticationManager);
        this.userDetailsRepository = userDetailsRepository;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws IOException, ServletException {
        String header = "Authorization";
        boolean isMissingToken = request.getHeader(header) == null || !request.getHeader(header).startsWith("Bearer ");

        if (isMissingToken) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = request.getHeader(header);
            String email = tokenService.validate(token);
            UserDetails user = userDetailsRepository.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken session = new UsernamePasswordAuthenticationToken(email, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(session);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        } finally {
            chain.doFilter(request, response);
        }
    }
}
