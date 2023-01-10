package com.dotetimer.dto;

import com.dotetimer.domain.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ReviewDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class ReviewReqDto {
        private String bad;
        private String good;
        private String plan;
        private LocalDate reviewedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class ReviewResDto {
        private String userName;
        private String bad;
        private String good;
        private String plan;
        private LocalDate reviewedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class ReviewLikeDto {
        private int likeCount;
    }
}
