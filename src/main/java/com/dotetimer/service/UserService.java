package com.dotetimer.service;

import com.dotetimer.domain.User;
import com.dotetimer.dto.UserDto.UserJwtReqDto;
import com.dotetimer.dto.UserDto.UserJwtResDto;
import com.dotetimer.dto.UserDto.UserSignReqDto;
import com.dotetimer.infra.exception.CustomException;
import com.dotetimer.infra.jwt.JwtTokenProvider;
import com.dotetimer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.dotetimer.infra.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor // final 초기화
@Transactional // readOnly
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public User signUp(UserSignReqDto userSignReqDto) {
        // Null 확인 생략
        // 유효성 확인
        if (!checkValidUser(userSignReqDto.getEmail(), userSignReqDto.getPassword()))
            throw new CustomException(DUPLICATE_RESOURCE);

        // 중복 확인
        userRepository.findByEmail(userSignReqDto.getEmail())
                .ifPresent(param -> {
                    throw new CustomException(DUPLICATE_RESOURCE); // 중복 키 예외 처리
                });

        User user = User.builder()
                .email(userSignReqDto.getEmail())
                .password(passwordEncoder.encode(userSignReqDto.getPassword()))
                .name(userSignReqDto.getEmail().split("@")[0])
                .introduction("")
                .roles(Collections.singletonList("ROLE_USER"))
                .img("")
                .build();

        // DB 저장
        userRepository.save(user);

        // 로그 출력
        log.info("회원가입 성공");
        return user;
    }

    // 로그인
    public UserJwtResDto signIn(UserSignReqDto userSignReqDto) {
        // Null 확인 생략
        // 유효성 확인
        if (!checkValidUser(userSignReqDto.getEmail(), userSignReqDto.getPassword()))
            throw new CustomException(INVALID_LOGIN);

        // Email 찾기
        User user = userRepository.findByEmail(userSignReqDto.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // Password 확인
        if (!passwordEncoder.matches(userSignReqDto.getPassword(), user.getPassword()))
            throw new CustomException(PASSWORD_NOT_FOUND);

        UserJwtResDto userJwtResDto = UserJwtResDto.builder()
                .userId(user.getId())
                .accessToken(createAccessJwtToken(user, user.getRoles()))
                .refreshToken(createRefreshJwtToken(user))
                .build();

        // 로그 출력
        log.info("로그인 성공");

        // access 토큰 및 refresh 토큰 생성
        return userJwtResDto;
    }

    // refresh 토큰 재발급
    public UserJwtResDto refreshToken(UserJwtReqDto userJwtReqDto) {
        // Email 찾기
        User user = userRepository.findByEmail(userJwtReqDto.getEmail())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // refresh token 유효성 확인
        if (!checkValidRefreshToken(user.getRefreshToken(), userJwtReqDto.getRefreshToken()))
            throw new CustomException(EXPIRE_AUTH_TOKEN);

        UserJwtResDto userJwtResDto = UserJwtResDto.builder()
                .userId(user.getId())
                .accessToken(createAccessJwtToken(user, user.getRoles()))
                .refreshToken(createRefreshJwtToken(user))
                .build();

        // 로그 출력
        log.info("토큰 발급 완료");

        return userJwtResDto;
    }

    // 유효성 확인
    private boolean checkValidUser(String email, String password) {
        if (email.split("@")[0].equals("")) return false;
        if (password.length() < 8) return false;
        return true;
    }

    // access token 발급
    private String createAccessJwtToken(User user, List<String> roles) {
        return jwtTokenProvider.createAccessToken(user.getUsername(), roles);
    }

    // refresh token 발급 및 DB 저장
    private String createRefreshJwtToken(User user) {
        String refreshTokenValue = UUID.randomUUID().toString().replace("-", ""); // 유일한 식별자 생성
        user.setRefreshToken(refreshTokenValue);
        userRepository.save(user);
        return jwtTokenProvider.createRefreshToken(refreshTokenValue);
    }

    // 서버의 refresh token, 클라이언트의 refresh token 비교
    private boolean checkValidRefreshToken(String requiredValue, String givenRefreshToken) {
        String givenValue = String.valueOf((jwtTokenProvider.getClaims((givenRefreshToken)).get("value")));
        if (!givenValue.equals(requiredValue)) return false; // refresh token 만료
        return true;
    }
}
