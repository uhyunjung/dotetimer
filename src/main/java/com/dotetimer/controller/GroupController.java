package com.dotetimer.controller;

import com.dotetimer.domain.User;
import com.dotetimer.dto.GroupDto;
import com.dotetimer.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/group")
public class GroupController {
    private final GroupService groupService;

    @PostMapping(path = "")
    public ResponseEntity<?> createGroup(@AuthenticationPrincipal User user, @Valid @RequestBody GroupDto.StudyGroupDto studyGroupDto) {
        groupService.createGroup(user, studyGroupDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{groupId}")
    public ResponseEntity<?> getGroup(@PathVariable int groupId) {
        return new ResponseEntity<>(groupService.getGroup(groupId), HttpStatus.OK);
    }

    @PutMapping(path = "/{groupId}")
    public ResponseEntity<?> updateGroup(@PathVariable int groupId, @Valid @RequestBody GroupDto.StudyGroupDto studyGroupDto) {
        groupService.updateGroup(groupId, studyGroupDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{groupId}")
    public ResponseEntity<?> deleteGroup(@AuthenticationPrincipal User user, @PathVariable int groupId) {
        groupService.deleteGroup(user, groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/join/{groupId}")
    public ResponseEntity<?> joinGroup(@AuthenticationPrincipal User user, @PathVariable int groupId) {
        return new ResponseEntity<>(groupService.joinGroup(user, groupId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/exit/{groupId}")
    public ResponseEntity<?> exitGroup(@AuthenticationPrincipal User user, @PathVariable int groupId) {
        groupService.exitGroup(user, groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> getGroupList(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(groupService.getGroupJoinList(user), HttpStatus.OK);
    }
}
