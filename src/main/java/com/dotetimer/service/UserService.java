package com.dotetimer.service;

import com.dotetimer.domain.User;
import com.dotetimer.dto.JwtDto.JwtReqDto;
import com.dotetimer.dto.JwtDto.JwtResDto;
import com.dotetimer.dto.UserDto.UserSignReqDto;
import com.dotetimer.exception.CustomException;
import com.dotetimer.jwt.JwtTokenProvider;
import com.dotetimer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.dotetimer.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public void signUp(UserSignReqDto userSignReqDto) {
        // Null 확인 생략
        // 유효성 확인
        if (!checkValidUser(userSignReqDto.getEmail(), userSignReqDto.getPassword()))
            throw new CustomException(DUPLICATE_RESOURCE);

        // 중복 확인
        userRepository.findByEmail(userSignReqDto.getEmail())
                .ifPresent(param -> {
                    throw new CustomException(DUPLICATE_RESOURCE); // 중복 키 예외 처리
                });

        // DB 저장
        userRepository.save(
                User.builder()
                        .email(userSignReqDto.getEmail())
                        .password(passwordEncoder.encode(userSignReqDto.getPassword()))
                        .name(userSignReqDto.getEmail().split("@")[0])
                        .introduction("")
                        .roles(Collections.singletonList("ROLE_USER"))
                        .profileImage("")
                        .build());
    }

    // 로그인
    public JwtResDto signIn(UserSignReqDto userSignReqDto) {
        // Null 확인 생략
        // 유효성 확인
        if (!checkValidUser(userSignReqDto.getEmail(), userSignReqDto.getPassword()))
            throw new CustomException(DUPLICATE_RESOURCE);

        // Email 찾기
        User user = userRepository.findByEmail(userSignReqDto.getEmail())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // Password 확인
        if (!passwordEncoder.matches(userSignReqDto.getPassword(), user.getPassword()))
            throw new CustomException(PASSWORD_NOT_FOUND);

        // access 토큰 및 refresh 토큰 생성
        return JwtResDto.builder()
                .userId(user.getId())
                .accessToken(createAccessJwtToken(user, user.getRoles()))
                .refreshToken(createRefreshJwtToken(user))
                .build();
    }

    // refresh 토큰 재발급
    public JwtResDto refreshToken(JwtReqDto jwtReqDto) {
        // Email 찾기
        User user = userRepository.findByEmail(jwtReqDto.getEmail())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // refresh token 유효성 확인
        if (!checkValidRefreshToken(user.getRefreshToken(), jwtReqDto.getRefreshToken()))
            throw new CustomException(EXPIRE_AUTH_TOKEN);

        return JwtResDto.builder()
                .userId(user.getId())
                .accessToken(createAccessJwtToken(user, user.getRoles()))
                .refreshToken(createRefreshJwtToken(user))
                .build();
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
