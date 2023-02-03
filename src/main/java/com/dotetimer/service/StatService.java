package com.dotetimer.service;

import com.dotetimer.domain.Coin;
import com.dotetimer.infra.Green;
import com.dotetimer.domain.GroupJoin;
import com.dotetimer.domain.User;
import com.dotetimer.dto.StatDto.*;
import com.dotetimer.infra.exception.CustomException;
import com.dotetimer.infra.exception.ErrorCode;
import com.dotetimer.infra.mapper.StatMapper;
import com.dotetimer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.dotetimer.infra.Green.*;

@Service
@RequiredArgsConstructor
@Transactional
public class StatService {
    private final UserRepository userRepository;

    // 날짜별 코인 개수
    public List<UserStatResDto> getMyStatList(User user) {
        List<UserStatResDto> userStatResDtos = new ArrayList<>();
        user.getCoins().stream()
                .sorted(Comparator.comparing(Coin::getStudiedAt).reversed()) //.filter(o -> o.isRecorded())
                .forEach(o -> { // 날짜별 총시간, 시간별 색깔
                    userStatResDtos.add(StatMapper.INSTANCE.toUserStatResDto(o, getGreen(o.getCoinCount())));
                });
        return userStatResDtos;
    }

    // 내가 속한 그룹들의 팀원별 날짜별 내림차순 총시간, 출석부, 시간별 색깔 통계
    public List<GroupsStatResDto> getGroupStatList(User user) {
        List<GroupsStatResDto> groupsStatResDtos = new ArrayList<>();
        user.getGroupJoins()
                .forEach(o -> {
                    // 그룹의 팀원별(참가 정보 확인) 날짜별(오늘부터 그룹 생성 날짜 내림차순) 통계 정보
                    Map<LocalDate, List<StatInfoDto>> statByDate = new TreeMap<>(Comparator.reverseOrder()); // 날짜 내림차순 정렬
                    // 팀원별
                    for (GroupJoin groupJoin : o.getStudyGroup().getGroupJoin()) {
                        User member = groupJoin.getUser();
                        List<StatInfoDto> statInfoDtos;
                        // 날짜별
                        for (Coin coin : member.getCoins()) {
                            if (!statByDate.containsKey(coin.getStudiedAt())) statByDate.put(coin.getStudiedAt(), new ArrayList<>());
                            statInfoDtos = statByDate.get(coin.getStudiedAt());
                            statInfoDtos.add(new StatInfoDto(member.getId(), member.getName(), coin.getCoinCount() * 10, true, getGreen(coin.getCoinCount()).getColorCode()));
                            statByDate.put(coin.getStudiedAt(), statInfoDtos);
                        }
                    }
                    for (List<StatInfoDto> l: statByDate.values()) { // 멤버 정렬
                        l.stream().sorted(Comparator.comparing(StatInfoDto::getUserId));
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

        return getMyStatList(user);
    }

    private Green getGreen(int coinCount) {
        if (coinCount == 0) return LEVEL_ZERO;
        else if (coinCount <= 2) return LEVEL_ONE;
        else if (coinCount <= 6) return LEVEL_TWO;
        else if (coinCount <= 10) return LEVEL_THREE;
        else return LEVEL_FOUR;
    }
}