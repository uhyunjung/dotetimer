package com.dotetimer.controller;

import com.dotetimer.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/profile")
public class ProfileController {
    @PutMapping(path = "/premium/{status}")
    public ResponseEntity<?> updatePremium(@AuthenticationPrincipal User user, @PathVariable String status) {
        updatePremium(user, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
