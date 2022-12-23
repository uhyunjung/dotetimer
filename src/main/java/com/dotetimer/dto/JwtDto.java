package com.dotetimer.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class JwtDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class JwtReqDto {
        @Email
        private String email;
        private String refreshToken;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class JwtResDto {
        private int userId;
        private String accessToken;
        private String refreshToken;
    }
}
