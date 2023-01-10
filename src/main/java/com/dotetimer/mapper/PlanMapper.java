package com.dotetimer.mapper;

import com.dotetimer.domain.Plan;
import com.dotetimer.domain.PlanInfo;
import com.dotetimer.domain.User;
import com.dotetimer.dto.PlanDto.PlanReqDto;
import com.dotetimer.dto.PlanDto.PlanResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface PlanMapper {
    PlanMapper INSTANCE = Mappers.getMapper(PlanMapper.class);

    // Entity -> DTO
    default PlanResDto toPlanResDto(Plan plan, PlanInfo planInfo) {
        return PlanResDto.builder()
                .title(planInfo.getTitle())
                .category(planInfo.getCategory())
                .color(planInfo.getColor())
                .startTime(plan.getStartTime())
                .endTime(plan.getEndTime())
                .repeatDay(planInfo.getRepeatDay())
                .completedAt(planInfo.getCompletedAt())
                .build();
    }
}
