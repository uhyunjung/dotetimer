package com.dotetimer.infra.mapper;

import com.dotetimer.domain.Plan;
import com.dotetimer.domain.PlanInfo;
import com.dotetimer.dto.PlanDto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlanMapper {
    PlanMapper INSTANCE = Mappers.getMapper(PlanMapper.class);

    // DTO -> Entity
    default PlanInfo toPlanInfo(PlanInfoReqDto planInfoReqDto) {
        return PlanInfo.builder()
                .title(planInfoReqDto.getTitle())
                .category(planInfoReqDto.getCategory())
                .color(planInfoReqDto.getColor())
                .repeatDay(planInfoReqDto.getRepeatDay())
                .completedAt(planInfoReqDto.getCompletedAt())
                .build();
    }

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
