package com.dotetimer.repository;

import com.dotetimer.domain.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.dotetimer.domain.QUser.*;
import static com.dotetimer.domain.QGroupJoin.*;
import static com.dotetimer.domain.QStudyGroup.*;
import static com.dotetimer.domain.QReview.*;
import static com.dotetimer.domain.QReviewLike.*;
import static com.dotetimer.domain.QPlan.*;

@RequiredArgsConstructor
public class QueryDSLRepositoryImpl implements QueryDSLRepository {
    private final JPAQueryFactory jpaQueryFactory;
    
    // 사용자 이름으로 검색
    @Override
    public List<User> findUsersByName(String userName) {
        return jpaQueryFactory
                .select(user)
                .from(user)
                .where(user.name.contains(userName))
                .fetch();
    }

    @Override
    public List<StudyGroup> findGroupsByKeyword(String keyword) {
        return jpaQueryFactory
                .select(studyGroup)
                .from(studyGroup)
                .where(searchGroup(keyword))
                .fetch();
    }
    
    // 그룹 키워드나 카테고리로 검색

//    // 사용자나 그룹으로 참가 정보 찾기
//    @Override
//    public GroupJoin findByUserAndGroup(int userId, int groupId) {
//        return jpaQueryFactory
//                .select(groupJoin)
//                .from(groupJoin)
//                .where(eqUserId(userId, 1), eqGroupId(groupId))
//                .fetchFirst();
//    }

    // groupId에 속한 사용자 리스트
    @Override
    public List<GroupJoin> findUsersByGroup(int groupId) {
        return jpaQueryFactory
                .selectFrom(groupJoin)
                .where(groupJoin.user.in(
                        JPAExpressions
                                .select(groupJoin.studyGroup.user)
                                .from(groupJoin)
                                .where(groupJoin.studyGroup.id.eq(groupId))))
                .fetch();
    }

//    // userId가 속한 그룹 리스트
//    @Override
//    public List<StudyGroup> findGroupsByUser(int userId) {
//        return jpaQueryFactory
//                .selectFrom(studyGroup)
//                .where(studyGroup.id.in(
//                        JPAExpressions
//                                .select(groupJoin.studyGroup.id)
//                                .from(groupJoin)
//                                .where(groupJoin.user.id.eq(userId))))
//                .fetch();
//    }

//    // 작성날짜로 하루세줄 찾기
//    @Override
//    public Review findByReviewedAt(int userId, LocalDate localDate) {
//        return jpaQueryFactory
//                .selectFrom(review)
//                .where(review.user.id.eq(userId), review.reviewedAt.eq(localDate))
//                .fetchFirst();
//    }

//    // 사용자나 하루세줄로 하루세줄 좋아요 찾기
//    @Override
//    public ReviewLike findByUserAndReview(int userId, int reviewId) {
//        return jpaQueryFactory
//                .select(reviewLike)
//                .from(reviewLike)
//                .where(eqUserId(userId, 2), eqReviewId(reviewId))
//                .fetchFirst();
//    }

    // 본인 하루세줄 제외하고 찾기
    @Override
    public List<Review> findReviewsExceptUser(int userId) {
        return jpaQueryFactory
                .selectFrom(review)
                .where(review.user.id.ne(userId)) // not equal
                .fetch();
    }

    @Override
    public List<ReviewLike> findLikesByReview(int reviewId) {
        return jpaQueryFactory
                .selectFrom(reviewLike)
                .where(reviewLike.review.id.eq(reviewId))
                .fetch();
    }

//    @Override
//    public List<Plan> findPlansByUserAndDate(boolean record, int userId, LocalDate studiedAt) {
//        return jpaQueryFactory
//                .select(plan)
//                .from(plan)
//                .where(plan.recorded.eq(record), eqUserId(userId, 3), eqPlanDate(studiedAt))
//                .fetch();
//    }

    private BooleanExpression eqUserId(int userId, int order) {
        if (!StringUtils.hasText(String.valueOf(userId))) return null;
        if (order == 1) return groupJoin.user.id.eq(userId); // groupJoin
        if (order == 2) return reviewLike.user.id.eq(userId); // reviewLike
        //if (order == 3) return plan.user.id.eq(userId); // plan
        return null;
    }

    private BooleanExpression eqGroupId(int groupId) {
        if (!StringUtils.hasText(String.valueOf(groupId))) return null;
        return groupJoin.studyGroup.id.eq(groupId);
    }

    private BooleanExpression eqReviewId(int reviewId) {
        if (!StringUtils.hasText(String.valueOf(reviewId))) return null;
        return reviewLike.review.id.eq(reviewId);
    }

//    private BooleanExpression eqPlanDate(LocalDate studiedAt) {
//        if (!StringUtils.hasText(String.valueOf(studiedAt))) return null;
//        return plan.studiedAt.eq(studiedAt);
//    }

    private BooleanBuilder searchGroup(String keyword) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!StringUtils.hasText(keyword)) return null;
        booleanBuilder.or(studyGroup.name.contains(keyword));
        booleanBuilder.or(studyGroup.details.contains(keyword));
        booleanBuilder.or(studyGroup.category.contains(keyword));
        return booleanBuilder;
    }
}
