package com.dotetimer.infra.mapper;

import com.dotetimer.domain.Review;
import com.dotetimer.domain.User;
import com.dotetimer.dto.ReviewDto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    // DTO -> Entity
    default Review toReview(User user, ReviewReqDto reviewReqDto) {
        return Review.builder()
                .user(user) // User에 review 추가
                .bad(reviewReqDto.getBad())
                .good(reviewReqDto.getGood())
                .plan(reviewReqDto.getPlan())
                .reviewedAt(reviewReqDto.getReviewedAt())
                .build();
    }

    // Entity -> DTO
    default ReviewResDto toReviewResDto(Review review) {
        return ReviewResDto.builder()
                .userName(review.getUser().getName())
                .bad(review.getBad())
                .good(review.getGood())
                .plan(review.getPlan())
                .reviewedAt(review.getReviewedAt())
                .build();
    }
}
