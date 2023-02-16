package com.dotetimer.service;

import com.dotetimer.domain.User;
import com.dotetimer.dto.StatDto.*;
import com.dotetimer.infra.exception.CustomException;
import com.dotetimer.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.dotetimer.infra.exception.ErrorCode.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("Stat Service Validation Test")
@Transactional
public class StatServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatService statService;

    @Test
    @DisplayName("Stat me success")
    void getMyStatListSuccess() throws CustomException {
        User user = userRepository.findByEmail("tester1@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        assertNotNull(user);
        List<UserStatResDto> myStatList = statService.getMyStatList(user);
        assertEquals(4, myStatList.stream().count());
    }

    @Test
    @DisplayName("Stat group success")
    void getGroupStatListSuccess() throws CustomException {
        User user = userRepository.findByEmail("tester1@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        assertNotNull(user);
        List<GroupsStatResDto> groupsStatList = statService.getGroupStatList(user);
        assertEquals(3, groupsStatList.stream().count());
    }

    @Test
    @DisplayName("Stat User success")
    void getUserStatListSuccess() throws CustomException {
        User user = userRepository.findByEmail("tester1@gmail.com")
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        assertNotNull(user);
        List<UserStatResDto> myStatList = statService.getUserStatList(user.getId());
        assertEquals(4, myStatList.stream().count());
    }
}
