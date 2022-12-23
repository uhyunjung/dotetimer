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
    public static class UserInfoResDto {
        @Email
        private String email;
        private String password;
        private String name;
        private String introduction;
        private boolean opened;
        private String profile_image;
        private boolean premium;
        private int coin_count;
    }
}

