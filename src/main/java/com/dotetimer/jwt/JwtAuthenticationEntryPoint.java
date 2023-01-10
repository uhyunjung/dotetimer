package com.dotetimer.jwt;

import com.dotetimer.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.dotetimer.exception.ErrorCode.EXPIRE_AUTH_TOKEN;
import static com.dotetimer.exception.ErrorCode.INVALID_AUTH_TOKEN;

// 401 Unauthorized Exception 처리(인증 실패)
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorResponse errorResponse;
        if (authException.equals(EXPIRE_AUTH_TOKEN)) {
            errorResponse = ErrorResponse.builder()
                    .status(EXPIRE_AUTH_TOKEN.getHttpStatus().value())
                    .error(EXPIRE_AUTH_TOKEN.getHttpStatus().name())
                    .message(EXPIRE_AUTH_TOKEN.getDetail())
                    .build();
        }
        else {
            errorResponse = ErrorResponse.builder()
                    .status(INVALID_AUTH_TOKEN.getHttpStatus().value())
                    .error(INVALID_AUTH_TOKEN.getHttpStatus().name())
                    .message(INVALID_AUTH_TOKEN.getDetail())
                    .build();
        }

        JSONObject json = new JSONObject();
        json.put("timestamp", errorResponse.getTimestamp());
        json.put("status", errorResponse.getStatus());
        json.put("error", errorResponse.getError());
        json.put("message", errorResponse.getMessage());
        json.put("path", request.getRequestURI());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Content-Type", "application/json");
        response.setCharacterEncoding("utf-8");
        // response.getWriter().write("401 Exception");
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
        response.sendRedirect("/api/user/sign_in"); // 마지막 순서 주의

        log.info(json.toString());
        log.error(authException.getMessage());
    }
}
