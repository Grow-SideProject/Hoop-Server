package com.hoop.api.config.filter;

import com.hoop.api.repository.UserRepository;
import com.hoop.api.service.auth.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;


import java.io.IOException;
@RequiredArgsConstructor
public class JwtTokenAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Jwt Provier 주입

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 이 부분에서 요청에서 필요한 정보를 추출하고 사용자 인증을 시도합니다.
        String accessToken = jwtService.resolveToken(request);
        String subject = jwtService.getSubject(accessToken);
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            // 비밀번호 검증 로직을 수행하고, 유효한 경우 사용자 인증 토큰을 생성합니다.
            if (passwordIsValid(userDetails, subject)) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (AuthenticationException authException) {
            // 사용자 인증에 실패한 경우 예외를 처리할 수 있습니다.
        }

        filterChain.doFilter(request, response);
    }

    private boolean passwordIsValid(UserDetails userDetails, String password) {
        // 실제로 비밀번호를 검증하는 로직을 구현해야 합니다.
        // 이 예제에서는 간단히 비밀번호가 "password"인 경우에만 유효하다고 가정합니다.
        return "password".equals(password);
    }
}