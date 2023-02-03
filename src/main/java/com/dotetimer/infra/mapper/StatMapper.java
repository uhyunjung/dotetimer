package com.dotetimer.infra.mapper;

import com.dotetimer.domain.Coin;
import com.dotetimer.dto.StatDto.*;
import com.dotetimer.infra.Green;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StatMapper {
    StatMapper INSTANCE = Mappers.getMapper(StatMapper.class);

    // Entity -> DTO
    default UserStatResDto toUserStatResDto(Coin coin, Green green) {
        return UserStatResDto.builder()
                .date(coin.getStudiedAt())
                .time(coin.getCoinCount() * 10) // 분 = 코인 * 10
                .coin(coin.getCoinCount())
                .green(green.getColorCode())
                .build();
    }
}
