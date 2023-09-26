package com.zerobase.daangnmarketclone.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.daangnmarketclone.dto.SignUpRequestDto;
import com.zerobase.daangnmarketclone.service.SignUpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SignUpController.class)
class SignUpControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SignUpService signUpService;

    @Test
    void 성공적으로_회원가입() throws Exception {
        // given
        SignUpRequestDto request = new SignUpRequestDto();
        request.setEmail("test@naver.com");
        request.setNickname("김민호");
        request.setPassword("password");

        given(signUpService.signUp(request.toUserDto())).willReturn(1L);

        mockMvc.perform(
            post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isCreated());

        // when
        // then
    }

}