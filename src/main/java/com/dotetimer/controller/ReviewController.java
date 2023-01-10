package com.dotetimer.controller;

import com.dotetimer.domain.User;
import com.dotetimer.dto.GroupDto;
import com.dotetimer.dto.ReviewDto.ReviewReqDto;
import com.dotetimer.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping(path = "")
    public ResponseEntity<?> createReview(@AuthenticationPrincipal User user, @Valid @RequestBody ReviewReqDto reviewReqDto) {
        reviewService.createReview(user, reviewReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{reviewId}")
    public ResponseEntity<?> getReview(@PathVariable int reviewId) {
        return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }

    @PutMapping(path = "/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable int reviewId, @Valid @RequestBody ReviewReqDto reviewReqDto) {
        reviewService.updateReview(reviewId, reviewReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{reviewId}")
    public ResponseEntity<?> deleteReview(@AuthenticationPrincipal User user, @PathVariable int reviewId) {
        reviewService.deleteReview(user, reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/like/{reviewId}")
    public ResponseEntity<?> getLikeCount(@PathVariable int reviewId) {
        return new ResponseEntity<>(reviewService.getLikeCount(reviewId), HttpStatus.OK);
    }

    @PostMapping(path = "/like/{reviewId}") // RequestParam key와 매개변수 이름 일치
    public ResponseEntity<?> likeReview(@AuthenticationPrincipal User user, @PathVariable int reviewId, @RequestParam String status) {
        reviewService.likeReview(user, reviewId, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> getReviewList(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(reviewService.getReviewList(user), HttpStatus.OK);
    }

    @GetMapping(path = "/list/me")
    public ResponseEntity<?> getReviewMyList(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(reviewService.getReviewMyList(user), HttpStatus.OK);
    }
}
