package com.dotetimer.service;

import com.dotetimer.domain.GroupJoin;
import com.dotetimer.domain.StudyGroup;
import com.dotetimer.domain.Theme;
import com.dotetimer.domain.User;
import com.dotetimer.dto.GroupDto.StudyGroupDto;
import com.dotetimer.dto.GroupDto.GroupJoinResDto;
import com.dotetimer.exception.CustomException;
import com.dotetimer.mapper.GroupMapper;
import com.dotetimer.repository.GroupJoinRepository;
import com.dotetimer.repository.StudyGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.dotetimer.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional // readOnly
public class GroupService {
    private final StudyGroupRepository studyGroupRepository;
    private final GroupJoinRepository groupJoinRepository;
    private final GroupMapper groupMapper;

    @Transactional
    public void createGroup(User user, StudyGroupDto studyGroupDto) {
        // Null 및 유효성 확인
        if (!checkValidGroup(studyGroupDto.getName(), studyGroupDto.getPassword()))
            throw new CustomException(INVALID_LOGIN);

        // DTO -> Entity
        StudyGroup studyGroup = StudyGroup.builder()
                .user(user) // User에 StudyGroup 추가
                .name(studyGroupDto.getName())
                .category(studyGroupDto.getCategory())
                .theme(Theme.valueOf(studyGroupDto.getTheme())) // string -> enum
                .joinCount(studyGroupDto.getJoinCount())
                .details(studyGroupDto.getDetails())
                .password(studyGroupDto.getPassword())
                .createdAt(LocalDate.now())
                .build();

        // DB 저장
        studyGroupRepository.save(studyGroup);
        groupJoinRepository.save(
                GroupJoin.builder()
                        .user(user) // User에 GroupJoin 추가
                        .studyGroup(studyGroup)
                        .joinedAt(LocalDate.now())
                        .build());
    }

    public StudyGroupDto getGroup(int groupId) {
        // 그룹 찾기
        StudyGroup studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // Entity -> DTO
        return groupMapper.INSTANCE.toStudyGroupDto(studyGroup);
    }

    @Transactional
    public void updateGroup(int groupId, StudyGroupDto studyGroupDto) {
        // Null 및 유효성 확인
        if (!checkValidGroup(studyGroupDto.getName(), studyGroupDto.getPassword()))
            throw new CustomException(INVALID_LOGIN);

        // 그룹 찾기
        StudyGroup studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // Entity 수정
        studyGroup.updateStudyGroup(studyGroupDto.getName(), studyGroupDto.getCategory(), Theme.valueOf(studyGroupDto.getTheme()), studyGroupDto.getJoinCount(), studyGroupDto.getDetails(), studyGroupDto.getPassword());

        // DB 저장
        studyGroupRepository.save(studyGroup);
    }

    @Transactional
    public void deleteGroup(User user, int groupId) {
        // 그룹 찾기
        StudyGroup studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // DB 삭제
        studyGroupRepository.delete(studyGroup); // Cascade로 GroupJoin 삭제

        // 그룹 참가 정보 찾기
        GroupJoin groupJoin = user.getGroupJoins().stream()
                .filter(o->o.getId() == groupId)
                .findFirst()
                .get();// groupJoinRepository.findByUserAndGroup(user.getId(), groupId);

        // User에 StudyGroup, GroupJoin 삭제
        user.getStudyGroups().remove(studyGroup);
        user.getGroupJoins().remove(groupJoin);
    }

    @Transactional
    public GroupJoinResDto joinGroup(User user, int groupId) {
        // 중복 확인 : 지연 로딩 vs DB 조회(groupJoinRepository.findByUserAndGroup(user.getId(), groupId) != null)
        if (user.getGroupJoins().stream()
                .filter(o -> o.getStudyGroup().getId() == groupId)
                .count() > 0)
            throw new CustomException(DUPLICATE_RESOURCE);

        // 그룹 찾기
        StudyGroup studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // DB 저장
        groupJoinRepository.save(
                GroupJoin.builder()
                        .user(user) // User에 GroupJoin 추가
                        .studyGroup(studyGroup)
                        .joinedAt(LocalDate.now())
                        .build());

        return GroupJoinResDto.builder()
                .joinCount(groupJoinRepository.findUsersByGroup(user.getId()).size()) // count
                .build();
    }

    @Transactional
    public void exitGroup(User user, int groupId) {
//        // 존재 여부 확인
//        if (user.get)// (groupJoinRepository.findByUserAndGroup(user.getId(), groupId) == null)
//            throw new CustomException(MEMBER_NOT_FOUND);

        // 그룹 참가 정보 확인
        GroupJoin groupJoin = user.getGroupJoins().stream()
                .filter(o -> o.getStudyGroup().getId() == groupId)
                .findFirst()
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND)); // groupJoinRepository.findByUserAndGroup(user.getId(), groupId);

        // DB 삭제
        groupJoinRepository.delete(groupJoin);

        // User에 GroupJoin 삭제
        user.getGroupJoins().remove(groupJoin);
    }

    // user.getGroups : 본인이 만든 그룹 리스트
    // getGroupJoinList : 본인이 속한 그룹 리스트
    public List<StudyGroupDto> getGroupJoinList(User user) {
        // 그룹 및 그룹 참가 정보 찾기
        List<StudyGroup> studyGroups = user.getStudyGroups().stream().toList(); // groupJoinRepository.findGroupsByUser(user.getId());
        List<StudyGroupDto> studyGroupDtos = new ArrayList<>();
        studyGroups.forEach(o -> {
                    // Entity 수정
                    o.updateStudyGroup(o.getName(), o.getCategory(), o.getTheme(), groupJoinRepository.findUsersByGroup(user.getId()).size(), o.getDetails(), o.getPassword());
                    // Entity -> DTO
                    studyGroupDtos.add(groupMapper.INSTANCE.toStudyGroupDto(o));
                }
        );
        return studyGroupDtos;
    }

    // Null 및 유효성 확인
    private boolean checkValidGroup(String name, String password) {
        if ((name == null) || (password == null)) return false;
        if ((name.length() < 2) || (name.length() > 100)) return false;
        if (password.length() < 8) return false;
        return true;
    }
}
