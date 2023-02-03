package com.dotetimer.service;

import com.dotetimer.domain.User;
import com.dotetimer.dto.UserDto.*;
import com.dotetimer.dto.UserDto.UserSignReqDto;
import com.dotetimer.infra.exception.CustomException;
import com.dotetimer.infra.jwt.JwtTokenProvider;
import com.dotetimer.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.dotetimer.infra.exception.ErrorCode.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("User Dto Validation Test")
@Transactional
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("User sign_up success")
    void signUpSuccess() throws CustomException {
        UserSignReqDto userSignReqDto = UserSignReqDto.builder()
                .email("test@gmail.com")
                .password("abcdefgh")
                .build();
        userService.signUp(userSignReqDto);
        User user = userRepository.findByEmail("test@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        assertEquals(user.getEmail(), userSignReqDto.getEmail());
    }

    @Test
    @DisplayName("User sign_in success")
    void signInSuccess() throws CustomException {
        UserSignReqDto userSignReqDto = UserSignReqDto.builder()
                .email("tester1@gmail.com")
                .password("abcdefgh")
                .build();
        UserJwtResDto userJwtResDto = userService.signIn(userSignReqDto);
        User user = userRepository.findByEmail("tester1@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        assertEquals(jwtTokenProvider.createRefreshToken(user.getRefreshToken()), userJwtResDto.getRefreshToken());
    }

    @Test
    @DisplayName("User refresh_token success")
    void refreshTokenSuccess() throws CustomException {
        User user = userRepository.findByEmail("tester1@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        UserJwtReqDto userJwtReqDto = UserJwtReqDto.builder()
                .email("tester1@gmail.com")
                .refreshToken(jwtTokenProvider.createRefreshToken(user.getRefreshToken()))
                .build();
        UserJwtResDto userJwtResDto = userService.refreshToken(userJwtReqDto);
        user = userRepository.findByEmail("tester1@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        assertEquals(jwtTokenProvider.createRefreshToken(user.getRefreshToken()), userJwtResDto.getRefreshToken());
    }
}
