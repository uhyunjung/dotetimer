package com.dotetimer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Green { // 하루 코인 개수 0 0~2 2~6 6~10 10+ / 총 코인 개수 0 0~12 12~36 36~60 60+
    LEVEL_ZERO(0, "#F1F8E9"), // 초록색 채도 50 200 400 600 900
    LEVEL_ONE(2, "#AED581"),
    LEVEL_TWO(6, "#9CCC65"),
    LEVEL_THREE(10, "#558B2F"),
    LEVEL_FOUR(24, "#33691E");
    private final int timeLimit;
    private final String colorCode;
}
