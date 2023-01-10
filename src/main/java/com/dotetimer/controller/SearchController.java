package com.dotetimer.controller;

import com.dotetimer.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/search")
public class SearchController {
    private final SearchService searchService;

    @GetMapping(path = "/user")
    public ResponseEntity<?> searchUser(@RequestParam(name = "userName", required = false) String userName) {
        return new ResponseEntity<>(searchService.searchUser(userName), HttpStatus.OK);
    }

    @GetMapping(path = "/group")
    public ResponseEntity<?> searchGroup(@RequestParam(name = "keyword", required = false) String keyword) {
        return new ResponseEntity<>(searchService.searchGroup(keyword), HttpStatus.OK);
    }
}
