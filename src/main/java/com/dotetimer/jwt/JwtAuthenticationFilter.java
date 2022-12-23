package com.dotetimer.jwt;

import com.dotetimer.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.dotetimer.exception.ErrorCode.INVALID_AUTH_TOKEN;

// Jwt 토큰 검증 요청 및 저장(Request마다 1회 실행)
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // GenericFilterBean 필터 두 번 작동
    private final JwtTokenProvider jwtTokenProvider;
    // 로그인하면 SecurityContext에 인증정보(authentication) 저장되어 있음
    // 인증 요청하기 전 설정( ex) 다른 어플리케이션에 인증정보 공유해야되는 경우)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // http 헤더에 access 토큰 추출(Bearer or X-AUTH-TOKEN으로 시작)
        String token = request.getHeader("X-AUTH-TOKEN");

        // 토큰 없는 경우, 토큰 잘못된 경우(시그니처 불일치), 토큰 만료된 경우
        if (token != null) {
            try {
                if (jwtTokenProvider.validateToken(token)) {
                    // Jwt 토큰을 SecurityContext에 인증정보(authentication = UsernamePasswordAuthenticationToken : principal, credential) 저장하여 사용
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            catch (CustomException e) {
                throw new CustomException(e.getErrorCode());
            }
        }
        // UsernamePasswordAuthenticationFilter(아이디 및 패스워드로 인증요청) 회원가입 및 로그인 실행
        filterChain.doFilter(request, response);
    }
}