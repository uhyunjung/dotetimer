package com.dotetimer.service;

import com.dotetimer.domain.StudyGroup;
import com.dotetimer.domain.User;
import com.dotetimer.dto.GroupDto.StudyGroupDto;
import com.dotetimer.dto.UserDto.UserInfoResDto;
import com.dotetimer.mapper.SearchMapper;
import com.dotetimer.repository.StudyGroupRepository;
import com.dotetimer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {
    private final UserRepository userRepository;
    private final StudyGroupRepository studyGroupRepository;

    public List<UserInfoResDto> searchUser(String userName) {
        // User 찾기
        List<User> users = userRepository.findUsersByName(userName);
        List<UserInfoResDto> userInfoResDtos = new ArrayList<>();
        users.forEach(o -> {
                    // Entity -> DTO
                    userInfoResDtos.add(SearchMapper.INSTANCE.toUserResDto(o));
                }
        );
        return userInfoResDtos;
    }

    public List<StudyGroupDto> searchGroup(String keyword) {
        List<StudyGroup> groups = studyGroupRepository.findGroupsByKeyword(keyword);
        List<StudyGroupDto> studyGroupDtos = new ArrayList<>();
        groups.forEach(o -> {
            // Entity -> DTO
            studyGroupDtos.add(SearchMapper.INSTANCE.toStudyGroupDto(o));
        });
        return studyGroupDtos;
    }
}
