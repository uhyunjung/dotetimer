package com.dotetimer.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.dotetimer.dto.PlanDto.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static com.dotetimer.infra.exception.ErrorCode.INVALID_DATA;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Plan Dto Validation Test")
public class PlanDtoTest {
    public static ValidatorFactory validatorFactory;
    public static Validator validator;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    @DisplayName("Plan post failed : Invalid completed_at")
    void invalidCompletedAt() {
        PlanInfoReqDto planInfoReqDto = PlanInfoReqDto.builder()
                .title("테스트")
                .category("백엔드")
                .color("BLUE")
                .repeatDay("월")
                .completedAt(LocalDate.parse("2010-01-30"))
                .build();
        Set<ConstraintViolation<PlanInfoReqDto>> violations = validator.validate(planInfoReqDto);
        violations.forEach(e -> {
            assertThat(e.getMessage()).isEqualTo(INVALID_DATA.getDetail());
        });
    }

    @Test
    @DisplayName("Plan post failed : Invalid time")
    void invalidTime() {
        PlanReqDto planReqDto = PlanReqDto.builder()
                .startTime(LocalTime.parse("20:30"))
                .endTime(LocalTime.parse("18:30"))
                .build();
        Set<ConstraintViolation<PlanReqDto>> violations = validator.validate(planReqDto);
        violations.forEach(e -> {
            assertThat(e.getMessage()).isEqualTo(INVALID_DATA.getDetail());
        });
    }
}