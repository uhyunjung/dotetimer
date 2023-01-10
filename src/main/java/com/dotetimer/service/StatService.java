package com.dotetimer.service;

import com.dotetimer.domain.Coin;
import com.dotetimer.domain.Green;
import com.dotetimer.domain.GroupJoin;
import com.dotetimer.domain.User;
import com.dotetimer.dto.StatDto.*;
import com.dotetimer.exception.CustomException;
import com.dotetimer.exception.ErrorCode;
import com.dotetimer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.dotetimer.domain.Green.*;

@Service
@RequiredArgsConstructor
@Transactional
public class StatService {
    private final UserRepository userRepository;

    // 시간 전달 vs 색깔 처리
    // Dto 여러 개의 리스트 vs Dto 안에 리스트 : DB로 부터 받기 편한 것
    // 날짜별 코인 개수
    public List<UserStatResDto> getMyStatList(User user) {
        List<UserStatResDto> userStatResDtos = new ArrayList<>();
        user.getCoins().stream()
                .sorted(Comparator.comparing(Coin::getStudiedAt).reversed()) // 코드 정렬 vs DB 정렬 //.filter(o -> o.isRecorded())
                .forEach(o -> { // 날짜별 총시간, 시간별 색깔
                    userStatResDtos.add(UserStatResDto.builder()
                            .date(o.getStudiedAt())
                            .time(o.getCoinCount() / 6)
                            .coin(o.getCoinCount())
                            .green(getGreen(o.getCoinCount()).getColorCode())
                            .build());
                });
        return userStatResDtos;
    }

    // 내가 속한 그룹들의 팀원별 날짜별 내림차순 총시간, 출석부, 시간별 색깔 통계
    public List<GroupsStatResDto> getGroupStatList(User user) {
        List<GroupsStatResDto> groupsStatResDtos = new ArrayList<>();
        user.getGroupJoins()
                .forEach(o -> {
                    // 그룹의 팀원별(참가 정보 확인) 날짜별(오늘부터 그룹 생성 날짜 내림차순) 통계 정보
                    Map<LocalDate, List<StatInfoDto>> statByDate = new HashMap<>();
                    // 팀원별
                    for (GroupJoin groupJoin : o.getStudyGroup().getGroupJoin()) {
                        User member = groupJoin.getUser();
                        List<StatInfoDto> statInfoDtos = new ArrayList<>();
                        // 날짜별
                        for (Coin coin : member.getCoins()) {
                            if (!statByDate.containsKey(coin.getStudiedAt()))
                                statByDate.put(coin.getStudiedAt(), statInfoDtos);
                            statInfoDtos = statByDate.get(coin.getStudiedAt());
                            statInfoDtos.add(new StatInfoDto(member.getUsername(), coin.getCoinCount() * 10, true, getGreen(coin.getCoinCount()).getColorCode()));
                            statByDate.put(coin.getStudiedAt(), statInfoDtos);
                        }
                    }

                    groupsStatResDtos.add(GroupsStatResDto.builder()
                            .groupId(o.getId())
                            .statByDate(statByDate)
                            .build());
                });
        return groupsStatResDtos;
    }

    // 사용자의 날짜별 코인 개수
    public List<UserStatResDto> getUserStatList(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        List<UserStatResDto> userStatResDtos = new ArrayList<>();
        user.getCoins().stream()
                .sorted(Comparator.comparing(Coin::getStudiedAt).reversed()) // 코드 정렬 vs DB 정렬 //.filter(o -> o.isRecorded())
                .forEach(o -> { // 날짜별 총시간, 시간별 색깔
                    userStatResDtos.add(UserStatResDto.builder()
                            .date(o.getStudiedAt())
                            .time(o.getCoinCount() / 6)
                            .coin(o.getCoinCount())
                            .green(getGreen(o.getCoinCount()).getColorCode())
                            .build());
                });
        return userStatResDtos;
    }

    private Green getGreen(int coinCount) {
        if (coinCount == 0) return LEVEL_ZERO;
        else if (coinCount <= 2) return LEVEL_ONE;
        else if (coinCount <= 6) return LEVEL_TWO;
        else if (coinCount <= 10) return LEVEL_THREE;
        else return LEVEL_FOUR;
    }
}