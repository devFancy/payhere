package com.payhere.owner.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.auth.application.AuthService;
import com.payhere.owner.application.OwnerService;
import com.payhere.owner.dto.SignupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.payhere.common.fixtures.OwnerFixtures.사장님_비밀번호;
import static com.payhere.common.fixtures.OwnerFixtures.사장님_휴대폰_번호;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
        OwnerController.class
})
@ActiveProfiles("test")
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OwnerService ownerService;

    @MockBean
    private AuthService authService;

    @DisplayName("사장님은 시스템에 휴대폰 번호와 비밀번호 입력을 통해서 회원가입을 할 수 있다.")
    @Test
    void signUp() throws Exception {
        // given
        SignupRequest request = SignupRequest.builder()
                .cellPhoneNumber(사장님_휴대폰_번호)
                .password(사장님_비밀번호)
                .build();

        // when & then
        mockMvc.perform(post("/owners/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

}