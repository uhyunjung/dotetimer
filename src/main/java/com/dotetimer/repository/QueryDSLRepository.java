package com.dotetimer.repository;


import com.dotetimer.domain.*;

import java.util.List;

// QueryDSL
public interface QueryDSLRepository {
    // User
    List<User> findUsersByName(String userName);

    // Group
    List<StudyGroup> findGroupsByKeyword(String keyword);
    // GroupJoin findByUserAndGroup(int userId, int groupId); // 동적 쿼리
    List<GroupJoin> findUsersByGroup(int groupId);
    // List<StudyGroup> findGroupsByUser(int userId);

    // Review
    // Review findByReviewedAt(int userId, LocalDate localDate);
    // ReviewLike findByUserAndReview(int userId, int reviewId); // 동적 쿼리
    List<Review> findReviewsExceptUser(int userId);
    List<ReviewLike> findLikesByReview(int reviewId);

    // Plan
    // List<Plan> findPlansByUserAndDate(boolean record, int userId, LocalDate studiedAt); // 동적 쿼리 : record 필수
}