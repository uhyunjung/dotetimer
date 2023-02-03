package com.dotetimer.service;

import com.dotetimer.domain.*;
import com.dotetimer.dto.ReviewDto.ReviewLikeDto;
import com.dotetimer.dto.ReviewDto.ReviewReqDto;
import com.dotetimer.dto.ReviewDto.ReviewResDto;
import com.dotetimer.infra.exception.CustomException;
import com.dotetimer.infra.mapper.ReviewMapper;
import com.dotetimer.repository.ReviewLikeRepository;
import com.dotetimer.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.dotetimer.infra.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    @Transactional
    public Review createReview(User user, ReviewReqDto reviewReqDto) {
        // Null 및 유효성 확인
        if (!checkValidReview(reviewReqDto.getBad(), reviewReqDto.getGood(), reviewReqDto.getPlan()))
            throw new CustomException(INVALID_DATA);

        // 중복 확인
        if (user.getReviews().stream()
                .filter(o -> o.getReviewedAt().equals(LocalDate.now()))
                .count() > 0)
            // (reviewRepository.findByReviewedAt(user.getId(), LocalDate.now()) != null)
            throw new CustomException(DUPLICATE_RESOURCE);

        // DTO -> Entity
        Review review = ReviewMapper.INSTANCE.toReview(user, reviewReqDto);

        // DB 저장
        reviewRepository.save(review);

        return review;
    }

    public ReviewResDto getReview(int reviewId) {
        // 하루세줄 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // Entity -> DTO
        return ReviewMapper.INSTANCE.toReviewResDto(review);
    }

    @Transactional
    public Review updateReview(int reviewId, ReviewReqDto reviewReqDto) {
        // Null 및 유효성 확인
        if (!checkValidReview(reviewReqDto.getBad(), reviewReqDto.getGood(), reviewReqDto.getPlan()))
            throw new CustomException(INVALID_DATA);
        
        // 하루세줄 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // Entity 수정
        review.updateReview(reviewReqDto.getBad(), reviewReqDto.getGood(), reviewReqDto.getPlan());
        
        // DB 저장
        reviewRepository.save(review);

        return review;
    }

    @Transactional
    public Review deleteReview(User user, int reviewId) {
        // 하루세줄 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        
        // DB 삭제
        reviewRepository.delete(review); // Cascade로 reviewLike 삭제

        // 하루세줄 좋아요 찾기
//        ReviewLike reviewLike = user.getReviewLikes().stream()
//                .filter(o -> o.getReview().getId() == reviewId)
//                .findFirst()
//                .get(); // reviewRepository.findByUserAndReview(user.getId(), reviewId);

        // User에 Review 삭제
        user.getReviews().remove(review); // user.getReviewLikes().remove(reviewLike);

        return review;
    }

    public ReviewLikeDto getLikeCount(int reviewId) {
        // 하루세줄 좋아요 찾기
        List<ReviewLike> reviewLikes = reviewRepository.findLikesByReview(reviewId);
        
        return ReviewLikeDto.builder()
                .likeCount(reviewLikes.size())
                .build();
    }

    @Transactional
    public ReviewLike likeReview(User user, int reviewId, String like) {
        // 하루세줄 및 하루세줄 좋아요 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        ReviewLike reviewLike = user.getReviewLikes().stream()
                .filter(o -> o.getReview().getId() == reviewId)
                .findFirst()
                .get(); // reviewRepository.findByUserAndReview(user.getId(), reviewId);

        if (reviewLike == null) {
            // 좋아요
            if (like.equals("true")) {
                reviewLikeRepository.save(
                        ReviewLike.builder()
                                .user(user)
                                .review(review)
                                .build());
            }
        } else {
            // 좋아요 취소
            if (like.equals("false")) {
                // DB 삭제
                reviewLikeRepository.delete(reviewLike);
                
                // User에 ReviewLike 삭제
                user.getReviewLikes().remove(reviewLike);
            }
        }

        return reviewLike;
    }

    // 본인 제외 하루세줄 리스트
    public List<ReviewResDto> getReviewList(User user) {
        // 하루세줄 찾기
        List<Review> reviews = reviewRepository.findReviewsExceptUser(user.getId());
        List<ReviewResDto> reviewResDtos = new ArrayList<>();
        reviews.forEach(o -> {
                     // Entity -> DTO
                    reviewResDtos.add(ReviewMapper.INSTANCE.toReviewResDto(o));
                }
        );
        return reviewResDtos;
    }

    // 본인 하루세줄 리스트
    public List<ReviewResDto> getReviewMyList(User user) {
        // 하루세줄 찾기
        List<Review> reviews = user.getReviews(); // reviewRepository.findAllByUser(user);
        List<ReviewResDto> reviewResDtos = new ArrayList<>();
        reviews.forEach(o -> {
                    // Entity -> DTO
                    reviewResDtos.add(ReviewMapper.INSTANCE.toReviewResDto(o));
                }
        );
        return reviewResDtos;
    }

    // Null 및 유효성 확인
    private boolean checkValidReview(String bad, String good, String plan) {
        if ((bad == null) || (good == null) || (plan == null)) return false;
        if ((bad.equals("")) && (good.equals("")) && (plan.equals(""))) return false;
        return true;
    }
}
