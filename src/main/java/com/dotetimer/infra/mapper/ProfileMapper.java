package com.dotetimer.infra.mapper;

import com.dotetimer.domain.User;
import com.dotetimer.dto.UserDto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    // Entity -> DTO
    UserInfoResDto toUserInfoResDto(User user);
}
