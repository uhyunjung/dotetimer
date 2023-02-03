package com.dotetimer.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.dotetimer.dto.GroupDto.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Group Dto Validation Test")
public class GroupDtoTest {
    public static ValidatorFactory validatorFactory;
    public static Validator validator;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    @DisplayName("Group post failed : Empty join_count")
    void emptyJoinCount() {
        StudyGroupDto studyGroupDto = StudyGroupDto.builder()
                .name("트리플에이")
                .category("중간고사 준비")
                .theme("BLUE")
                .details("환영해요")
                .password("12345678")
                .build();
        Set<ConstraintViolation<StudyGroupDto>> violations = validator.validate(studyGroupDto);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Group post failed : Invalid join_count")
    void invalidJoinCount() {
        StudyGroupDto studyGroupDto = StudyGroupDto.builder()
                .name("트리플에이")                .category("중간고사 준비")
                .theme("BLUE")
                .joinCount(-1)
                .details("환영해요")
                .password("12345678")
                .build();
        Set<ConstraintViolation<StudyGroupDto>> violations = validator.validate(studyGroupDto);
        assertThat(violations).isEmpty();
    }
}
