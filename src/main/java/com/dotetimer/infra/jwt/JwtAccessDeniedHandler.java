package com.dotetimer.infra.jwt;

import com.dotetimer.infra.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import org.json.JSONObject;

import static com.dotetimer.infra.exception.ErrorCode.FORBIDDEN;

// 403 Forbidden Exception 처리(권한 제한)
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorResponse errorResponse;
        errorResponse = ErrorResponse.builder()
                .status(FORBIDDEN.getHttpStatus().value())
                .error(FORBIDDEN.getHttpStatus().name())
                .message(FORBIDDEN.getDetail())
                .build();

        JSONObject json = new JSONObject();
        json.put("timestamp", errorResponse.getTimestamp());
        json.put("status", errorResponse.getStatus());
        json.put("error", errorResponse.getError());
        json.put("message", errorResponse.getMessage());
        json.put("path", request.getRequestURI());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("Content-Type", "application/json"); // response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();

        log.info(json.toString());
        log.error(accessDeniedException.getMessage());
    }
}
