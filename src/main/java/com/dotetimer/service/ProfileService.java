package com.dotetimer.service;

import com.dotetimer.domain.Donate;
import com.dotetimer.domain.Follow;
import com.dotetimer.domain.User;
import com.dotetimer.dto.ProfileDto.*;
import com.dotetimer.dto.UserDto.*;
import com.dotetimer.infra.exception.CustomException;
import com.dotetimer.infra.exception.ErrorCode;
import com.dotetimer.infra.mapper.ProfileMapper;
import com.dotetimer.repository.DonateRepository;
import com.dotetimer.repository.FollowRepository;
import com.dotetimer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.dotetimer.infra.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {
    private final UserRepository userRepository;
    private final DonateRepository donateRepository;
    private final FollowRepository followRepository;

    public UserInfoResDto getInfo(User user) {
        return ProfileMapper.INSTANCE.toUserInfoResDto(user);
    }

    public User updateInfo(User user, UserInfoReqDto userInfoReqDto) {
        user.updateInfo(userInfoReqDto.getName(), userInfoReqDto.getIntroduction(), userInfoReqDto.isOpened(), userInfoReqDto.getImg());
        userRepository.save(user);

        return user;
    }

    public Donate createDonate(User user, DonateDto donateDto) {
        // 기부 가능
        if (user.getCoinCount() >= donateDto.getCoinCount()) {
            user.updateCoin(user.getCoinCount() - donateDto.getCoinCount());
            Donate donate = Donate.builder()
                    .user(user)
                    .coinCount(donateDto.getCoinCount())
                    .donatedAt(LocalDate.now())
                    .build();
            donateRepository.save(donate);
            return donate;
        }
        // 기부 불가능
        else throw new CustomException(ErrorCode.INVALID_DATA);
    }

    public Follow followUser(User user, int followingId, boolean status) {
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // 본인 팔로우 제외
        if (user.getId() == following.getId()) throw new CustomException(ErrorCode.INVALID_DATA);

        // 팔로우
        if (status) {
            // 중복 확인
            if (user.getFollowers().stream()
                    .filter(o -> o.getFollowing().getId() == followingId)
                    .count() > 0)
                throw new CustomException(DUPLICATE_RESOURCE);

            Follow follow = Follow.builder()
                    .follower(user)
                    .following(following)
                    .build();
            
            // DB 저장
            followRepository.save(follow);

            return follow;
        }
        // 팔로우 취소
        else {
            Follow follow = followRepository.findByFollowerAndFollowing(user, following)
                    .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

            // DB 삭제
            followRepository.delete(follow);

            // User에 Follower 삭제
            user.getFollowers().remove(follow);

            return follow;
        }
    }

    public User signOut(User user) {
        user.updateRefreshToken(null);
        userRepository.save(user);

        return user;
    }

    public User deleteUser(User user) {
        userRepository.delete(user);
        return user;
    }
}
