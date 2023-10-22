package com.hoop.api.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.service.auth.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class JwtTokenAuthFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper;

    private  JwtService jwtService;


    public JwtTokenAuthFilter(String loginUrl, ObjectMapper objectMapper, JwtService jwtService) {
        super(loginUrl);
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String accessToken = jwtService.resolveToken((HttpServletRequest) request);
        String uuid = jwtService.getSubject(accessToken);
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                uuid,
                uuid
        );
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
    }
}
