package com.dotetimer.infra.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now().withNano(0); // 밀리sec 제외
    private final int status;
    private final String error;
    private final String message;
}