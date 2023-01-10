package com.dotetimer.mapper;

import com.dotetimer.domain.Review;
import com.dotetimer.dto.ReviewDto.ReviewResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

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
