package com.dotetimer.service;

import com.dotetimer.infra.exception.CustomException;
import com.dotetimer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.dotetimer.infra.exception.ErrorCode.MEMBER_NOT_FOUND;

// 인증 과정 중 DB에서 User 데이터 조회
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }
}
