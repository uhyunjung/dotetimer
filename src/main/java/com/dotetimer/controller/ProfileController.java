package com.dotetimer.controller;

import com.dotetimer.domain.User;
import com.dotetimer.dto.ProfileDto.DonateDto;
import com.dotetimer.dto.UserDto.UserInfoReqDto;
import com.dotetimer.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/profile")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping(path = "/info")
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(profileService.getInfo(user), HttpStatus.OK);
    }

    @PutMapping(path = "/info")
    public ResponseEntity<?> updateInfo(@AuthenticationPrincipal User user, @RequestBody UserInfoReqDto userInfoReqDto) {
        profileService.updateInfo(user, userInfoReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/donate")
    public ResponseEntity<?> createDonate(@AuthenticationPrincipal User user, @RequestBody DonateDto donateDto) {
        profileService.createDonate(user, donateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/follow/{userId}")
    public ResponseEntity<?> followUser(@AuthenticationPrincipal User user, @PathVariable int userId, @RequestParam(name = "status") boolean status) {
        profileService.followUser(user, userId, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/sign_out")
    public ResponseEntity<?> signOut(@AuthenticationPrincipal User user) {
        profileService.signOut(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal User user) {
        profileService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
