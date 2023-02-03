package com.dotetimer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class UserSignReqDto {
        @Email
        private String email;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class UserInfoReqDto {
        private String name;
        private String introduction;
        private boolean opened;
        private String img;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class UserInfoResDto {
        private String name;
        private String introduction;
        private boolean opened;
        private String img;
        private boolean premium;
        private int coinCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserJwtReqDto {
        @Email
        private String email;
        private String refreshToken;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserJwtResDto {
        private int userId;
        private String accessToken;
        private String refreshToken;
    }
}

