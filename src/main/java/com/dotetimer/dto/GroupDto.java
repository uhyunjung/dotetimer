package com.dotetimer.dto;

import com.dotetimer.domain.Green;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

public class GroupDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class StudyGroupDto {
        private String name;
        private String category;
        private String theme;
        private int joinCount;
        private String details;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class GroupJoinResDto {
        private int joinCount;
    }
}
