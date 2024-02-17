package com.payhere.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.auth.application.AuthService;
import com.payhere.auth.dto.LoginOwner;
import com.payhere.auth.dto.request.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.payhere.common.fixtures.OwnerFixtures.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
        AuthController.class
})
@ActiveProfiles("test")
class AuthControllerTest {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer aaaaaaaa.bbbbbbbb.cccccccc";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @DisplayName("사장님은 회원 가입이후, 로그인을 할 수 있다.")
    @Test
    void login() throws Exception {
        // given
        LoginRequest request = LoginRequest.builder()
                .cellPhoneNumber(사장님_휴대폰_번호)
                .password(사장님_비밀번호)
                .build();

        // when & then
        mockMvc.perform(post("/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("로그인한 사장님은 로그아웃을 할 수 있다.")
    @Test
    void logout() throws Exception {
        // given
        LoginOwner loginOwner = new LoginOwner(OWNER_ID);

        // when & then
        mockMvc.perform(get("/auth/logout")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginOwner))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}