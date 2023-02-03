package com.dotetimer.infra.mapper;

import com.dotetimer.domain.GroupJoin;
import com.dotetimer.domain.StudyGroup;
import com.dotetimer.domain.User;
import com.dotetimer.dto.GroupDto.StudyGroupDto;
import com.dotetimer.infra.Theme;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    // DTO -> Entity(기준 : 두 번 이상 사용하거나 매개변수 전달 개수 적을 때)
    default StudyGroup toStudyGroup(User user, StudyGroupDto studyGroupDto) {
        return StudyGroup.builder()
                .user(user) // User에 StudyGroup 추가
                .name(studyGroupDto.getName())
                .category(studyGroupDto.getCategory())
                .theme(Theme.valueOf(studyGroupDto.getTheme())) // string -> enum
                .joinCount(studyGroupDto.getJoinCount())
                .details(studyGroupDto.getDetails())
                .password(studyGroupDto.getPassword())
                .createdAt(LocalDate.now())
                .build();
    }

     default GroupJoin toGroupJoin(User user, StudyGroup studyGroup) {
        return GroupJoin.builder()
                .user(user) // User에 GroupJoin 추가
                .studyGroup(studyGroup)
                .joinedAt(LocalDate.now())
                .build();
     }

    // Entity -> DTO
    StudyGroupDto toStudyGroupDto(StudyGroup studyGroup);
}
