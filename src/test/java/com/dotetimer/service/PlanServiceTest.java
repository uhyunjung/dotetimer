package com.dotetimer.service;

import com.dotetimer.domain.Plan;
import com.dotetimer.domain.PlanInfo;
import com.dotetimer.domain.User;
import com.dotetimer.dto.PlanDto.*;
import com.dotetimer.infra.exception.CustomException;
import com.dotetimer.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.dotetimer.infra.exception.ErrorCode.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Plan Service Validation Test")
@Transactional
public class PlanServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlanService planService;

    @Test
    @DisplayName("Plan add plan info success")
    void addPlanInfoSuccess() throws CustomException {
        User user = userRepository.findByEmail("tester1@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        assertNotNull(user);

        PlanInfoReqDto planInfoReqDto = PlanInfoReqDto.builder()
                .title("테스트")
                .category("백엔드")
                .color("BLUE")
                .repeatDay("월")
                .completedAt(LocalDate.parse("2010-01-30"))
                .build();
        PlanInfo planInfo = planService.createPlanInfo(user, planInfoReqDto);

        assertEquals("테스트", planInfo.getTitle());
        assertEquals(0, planInfo.getPlans().size());
    }

    @Test
    @DisplayName("Plan update plan info success")
    void updatePlanInfoSuccess() throws CustomException {
        User user = userRepository.findByEmail("tester1@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        assertNotNull(user);

        PlanInfoReqDto planInfoReqDto = PlanInfoReqDto.builder()
                .title("테스트")
                .category("백엔드")
                .color("BLUE")
                .repeatDay("월")
                .completedAt(LocalDate.parse("2010-01-30"))
                .build();
        PlanInfo planInfo = planService.updatePlanInfo(planInfoReqDto, 1);

        assertEquals(planInfo.getTitle(), "테스트");
    }

    @Test
    @DisplayName("Plan add plan or record success")
    void addPlanOrRecordSuccess() throws CustomException {
        User user = userRepository.findByEmail("tester1@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        assertNotNull(user);

        PlanReqDto planReqDto = PlanReqDto.builder()
                .startTime(LocalTime.parse("18:30"))
                .endTime(LocalTime.parse("20:30"))
                .build();
        Plan plan = planService.createPlanOrRecord(user, planReqDto, 1, true);

        assertTrue(plan.isRecorded());
        assertEquals(12, plan.getCoin().getCoinCount());
        assertEquals(plan.getStartTime(), LocalTime.parse("18:30"));
        assertEquals(plan.getEndTime(), LocalTime.parse("20:30"));
    }

    @Test
    @DisplayName("Plan update plan or record success")
   void updatePlanOrRecordSuccess() throws CustomException {
        User user = userRepository.findByEmail("tester1@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        assertNotNull(user);

        PlanReqDto planReqDto = PlanReqDto.builder()
                .startTime(LocalTime.parse("18:30"))
                .endTime(LocalTime.parse("20:30"))
                .build();
        Plan plan = planService.updatePlanOrRecord(user, planReqDto, 6, true);

        assertTrue(plan.isRecorded());
        assertEquals(12, plan.getCoin().getCoinCount());
        assertEquals(plan.getStartTime(), LocalTime.parse("18:30"));
        assertEquals(plan.getEndTime(), LocalTime.parse("20:30"));
    }

    @Test
    @DisplayName("Plan delete plan success")
    void deletePlanSuccess() throws CustomException {
        User user = userRepository.findByEmail("tester1@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        assertNotNull(user);

        Plan plan = planService.deletePlan(user, 1);
        assertNotNull(plan);
    }
}
