package com.dotetimer.controller;

import com.dotetimer.dto.UserDto;
import com.dotetimer.dto.UserDto.UserSignReqDto;
import com.dotetimer.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/user")
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/sign_up")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserSignReqDto userSignReqDto) {
        userService.signUp(userSignReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/sign_in")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSignReqDto userSignReqDto) {
        return new ResponseEntity<>(userService.signIn(userSignReqDto), HttpStatus.OK);
    }

    @PostMapping(path = "/refresh_token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody UserDto.UserJwtReqDto userJwtReqDto) {
        return new ResponseEntity<>(userService.refreshToken(userJwtReqDto), HttpStatus.OK);
    }
}
