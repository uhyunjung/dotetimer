package com.dotetimer.controller;

import com.dotetimer.domain.User;
import com.dotetimer.service.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/stat/")
public class StatController {
    private final StatService statService;

    @GetMapping(path = "/me")
    public ResponseEntity<?> getMyStatList(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(statService.getMyStatList(user), HttpStatus.OK);
    }

    @GetMapping(path = "/me/group")
    public ResponseEntity<?> getGroupStatList(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(statService.getGroupStatList(user), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<?> getUserStatList(@PathVariable int userId) {
        return new ResponseEntity<>(statService.getUserStatList(userId), HttpStatus.OK);
    }
}
