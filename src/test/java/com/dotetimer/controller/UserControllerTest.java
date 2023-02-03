package com.dotetimer.controller;

import com.dotetimer.dto.UserDto.*;
import com.dotetimer.infra.exception.ErrorCode;
import com.dotetimer.infra.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DisplayName("User Controller Test") // 통합테스트, 나머지 Postman 테스트
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 방지 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("User sign_up success")
    void signUpSuccess() throws Exception {
        UserSignReqDto userSignReqDto = UserSignReqDto.builder()
                .email("test@gmail.com")
                .password("abcdefgh")
                .build();
        String jsonString = objectMapper.writeValueAsString(userSignReqDto);
        mockMvc.perform(post("http://localhost:8080/api/user/sign_up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("User sign_up failed : Duplicate Email")
    void signUpFailDuplicate() throws Exception {
        UserSignReqDto userSignReqDto = UserSignReqDto.builder()
                .email("tester1@gmail.com")
                .password("abcdefgh")
                .build();
        String jsonString = objectMapper.writeValueAsString(userSignReqDto);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.DUPLICATE_RESOURCE.getHttpStatus().value())
                .error(ErrorCode.DUPLICATE_RESOURCE.getHttpStatus().name())
                .message(ErrorCode.DUPLICATE_RESOURCE.getDetail())
                .build();
        String response = objectMapper.writeValueAsString(errorResponse);
        mockMvc.perform(post("http://localhost:8080/api/user/sign_up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("User sign_in failed : Wrong Email")
    void signInFailWrongEmail() throws Exception {
        UserSignReqDto userSignReqDto = UserSignReqDto.builder()
                .email("wrong@gmail.com")
                .password("abcdefgh")
                .build();
        String jsonString = objectMapper.writeValueAsString(userSignReqDto);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.USER_NOT_FOUND.getHttpStatus().value())
                .error(ErrorCode.USER_NOT_FOUND.getHttpStatus().name())
                .message(ErrorCode.USER_NOT_FOUND.getDetail())
                .build();
        String response = objectMapper.writeValueAsString(errorResponse);
        mockMvc.perform(post("http://localhost:8080/api/user/sign_in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("User sign_in failed : Wrong Password")
    void signInFailWrongPassword() throws Exception {
        UserSignReqDto userSignReqDto = UserSignReqDto.builder()
                .email("tester1@gmail.com")
                .password("ijklmnop")
                .build();
        String jsonString = objectMapper.writeValueAsString(userSignReqDto);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.PASSWORD_NOT_FOUND.getHttpStatus().value())
                .error(ErrorCode.PASSWORD_NOT_FOUND.getHttpStatus().name())
                .message(ErrorCode.PASSWORD_NOT_FOUND.getDetail())
                .build();
        String response = objectMapper.writeValueAsString(errorResponse);
        mockMvc.perform(post("http://localhost:8080/api/user/sign_in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(response));
    }
}
