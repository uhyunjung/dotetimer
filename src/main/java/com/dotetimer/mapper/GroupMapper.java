package com.dotetimer.mapper;

import com.dotetimer.domain.StudyGroup;
import com.dotetimer.dto.GroupDto.StudyGroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    // Entity -> DTO
    StudyGroupDto toStudyGroupDto(StudyGroup studyGroup);
}
