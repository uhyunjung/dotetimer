package com.dotetimer.infra;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Theme {
    RED, ORANGE, YELLOW, GREEN, BLUE, NAVY, PURPLE, WHITE, BLANK, GREY;

    @JsonCreator // 생성자 + Setter : deserialize(역직렬화) 한 후에 해당 객체가 immutable(불변)하기 위해 setter 없어진다
    public static Theme name(String value) {
        return Theme.valueOf(value.toUpperCase());
    }
}