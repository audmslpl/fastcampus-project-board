package com.fastcampus.projectboard.controller;

import com.fastcampus.projectboard.config.SecurityConfig;
import com.fastcampus.projectboard.config.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@DisplayName("View 컨트롤러 - 인증")
@Import(TestSecurityConfig.class)
@WebMvcTest(AuthControllerTest.EmptyController.class)
public class AuthControllerTest {
    private final MockMvc mvc;

    public AuthControllerTest(@Autowired MockMvc mvc) {

        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 로그인 페이지 - 정상 호출")
    @Test
    void givenNothing_whenLoggingIn_thenReturnsLoginView() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
    @TestComponent
    static class EmptyController {}

}
