package com.dotetimer.mapper;

import com.dotetimer.domain.StudyGroup;
import com.dotetimer.domain.User;
import com.dotetimer.dto.GroupDto.StudyGroupDto;
import com.dotetimer.dto.UserDto.UserInfoResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SearchMapper {
    SearchMapper INSTANCE = Mappers.getMapper(SearchMapper.class);

    // Entity -> DTO
    UserInfoResDto toUserResDto(User user);
    StudyGroupDto toStudyGroupDto(StudyGroup studyGroup);
}
