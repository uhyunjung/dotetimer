package com.dotetimer.dto;

import com.dotetimer.domain.Green;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class StatDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class UserStatResDto {
        private LocalDate date;
        private int time;
        private int coin;
        private String green;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class GroupsStatResDto {
        int groupId;
        private Map<LocalDate, List<StatInfoDto>> statByDate; // 일간 -> 주간, 월간 계산 가능
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class StatInfoDto {
        private String userName;
        private int totalTime;
        private boolean attendance;
        private String green;
    }
}
