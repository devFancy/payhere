package com.payhere.auth.application;

import com.payhere.auth.domain.AuthAccessToken;
import com.payhere.global.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JpaAuditingConfig.class)
@ActiveProfiles("test")
@SpringBootTest
class AuthTokenCreatorTest {

    @Autowired
    private TokenCreator tokenCreator;

    @DisplayName("액세스 토큰을 발급한다.")
    @Test
    void createAuthToken() {
        // given
        Long ownerId = 1L;

        // when
        AuthAccessToken authAccessToken = tokenCreator.createAuthToken(ownerId);

        // then
        assertThat(authAccessToken.getAccessToken()).isNotEmpty();
    }

    @DisplayName("토큰에서 페이로드를 추출한다.")
    @Test
    void extractPayLoad() {
        // given
        Long ownerId = 1L;
        AuthAccessToken authAccessToken = tokenCreator.createAuthToken(ownerId);

        // when
        Long actual = tokenCreator.extractPayLoad(authAccessToken.getAccessToken());

        // then
        assertThat(actual).isEqualTo(ownerId);
    }
}