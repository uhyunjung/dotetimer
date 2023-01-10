package com.dotetimer.dto;

import com.dotetimer.domain.Plan;
import com.dotetimer.domain.PlanInfo;
import com.dotetimer.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

public class PlanDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class PlanReqDto {
        private String title;
        private String category;
        private String color;
        private LocalTime startTime;
        private LocalTime endTime;
        private String repeatDay;
        private LocalDate completedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class PlanResDto {
        private String title;
        private String category;
        private String color;
        private LocalTime startTime;
        private LocalTime endTime;
        private String repeatDay;
        private LocalDate completedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class RecordReqDto {
        private LocalTime startTime;
        private LocalTime endTime;
    }
}
