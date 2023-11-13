package com.hoop.api.config.filter;

import com.hoop.api.exception.TokenInvalid;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.service.auth.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

public class JwtTokenAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    // Jwt Provier 주입
    // 생성자에 AuthenticationFailureHandler 추가
    public JwtTokenAuthFilter(JwtService jwtService, UserDetailsService userDetailsService, UserRepository userRepository, AuthenticationFailureHandler authenticationFailureHandler) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 이 부분에서 요청에서 필요한 정보를 추출하고 사용자 인증을 시도합니다.
            String token = jwtService.resolveToken(request);
            String subject = jwtService.getSubject(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // 인증 실패 시 핸들러 호출
            authenticationFailureHandler.onAuthenticationFailure(request, response, new TokenInvalid(""));
        }
    }
}