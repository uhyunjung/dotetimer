package com.dotetimer.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.dotetimer.dto.UserDto.UserSignReqDto;
import com.dotetimer.dto.JwtDto.JwtReqDto;

@DisplayName("User Dto Validation Test")
class UserDtoTest {
    @Test
    @DisplayName("User sign_up/sign_in")
    void emptyEmail() {
        UserSignReqDto user = UserSignReqDto.builder()
                .password("abc")
                .build();
    }

    @Test
    @DisplayName("User sign_up/sign_in")
    void invalidEmail() {
        UserSignReqDto user = UserSignReqDto.builder()
                .email("wrong email")
                .password("abc")
                .build();
    }

    @Test
    @DisplayName("User sign_up/sign_in")
    void emptyPassword() {
        UserSignReqDto user = UserSignReqDto.builder()
                .email("test@tester.com")
                .build();
    }

    @Test
    @DisplayName("User sign_up/sign_in")
    void wrongPassword() {
        UserSignReqDto user = UserSignReqDto.builder()
                .email("test@tester.com")
                .password("abc")
                .build();
    }

    @Test
    @DisplayName("User refresh_token")
    void wrongAccessToken() {
        JwtReqDto jwtReqDto = JwtReqDto.builder()
                .email("test@tester.com")
                .build();
    }

    @Test
    @DisplayName("User refresh_token")
    void wrongRefreshToken() {
        JwtReqDto jwtReqDto = JwtReqDto.builder()
                .email("test@tester.com")
                .refreshToken("wrong token")
                .build();
    }
}