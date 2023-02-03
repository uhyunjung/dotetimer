package com.dotetimer.dto;

import com.dotetimer.infra.exception.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.dotetimer.dto.UserDto.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
// Matcher : assertTrue; assertEquals(expected, actual); assertThat(actual).isEqualTo(expected); assertNotNull; assertThatThrownBy
// import static org.junit.jupiter.api.Assertions.*; import static org.springframework.test.util.AssertionErrors.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("User Dto Validation Test") // 사용자 입력
class UserDtoTest {
    public static ValidatorFactory validatorFactory;
    public static Validator validator;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    @DisplayName("User sign_up failed : Empty email")
    void emptyEmail() {
        UserSignReqDto userSignReqDto = UserSignReqDto.builder()
                .password("abcdefgh")
                .build();
        Set<ConstraintViolation<UserSignReqDto>> violations = validator.validate(userSignReqDto);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("User sign_in failed : Empty password")
    void emptyPassword() {
        UserSignReqDto userSignReqDto = UserSignReqDto.builder()
                .email("tester1@gmail.com")
                .build();
        Set<ConstraintViolation<UserSignReqDto>> violations = validator.validate(userSignReqDto);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("User sign_up failed : Invalid email")
    void invalidEmail() {
        UserSignReqDto userSignReqDto = UserSignReqDto.builder()
                .email("invalid email")
                .password("abcdefgh")
                .build();
        Set<ConstraintViolation<UserSignReqDto>> violations = validator.validate(userSignReqDto);
        violations.forEach(e -> {
            assertThat(e.getMessage()).isEqualTo(ErrorCode.INVALID_EMAIL.getDetail());
        });
    }

    @Test
    @DisplayName("User refresh_token failed : Wrong refresh_token")
    void signInFailWrongRefreshToken() {
        UserJwtReqDto userJwtReqDto = UserJwtReqDto.builder()
                .email("tester1@gmail.com")
                .refreshToken("wrong token")
                .build();
        Set<ConstraintViolation<UserJwtReqDto>> violations = validator.validate(userJwtReqDto);
        assertThat(violations).isEmpty();
    }
}