package com.payhere.auth.application;

import com.payhere.auth.exception.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenProviderTest {

    private static final String JWT_SECRET_KEY = "A".repeat(32); // Secret Key는 최소 32바이트 이상이어야함.
    private static final int JWT_ACCESS_TOKEN_EXPIRE_LENGTH = 3600;
    private static final String PAYLOAD = "payload";

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(JWT_SECRET_KEY, JWT_ACCESS_TOKEN_EXPIRE_LENGTH);

    @DisplayName("액세스 토큰을 생성한다.")
    @Test
    void createAccessToken() {
        // given & when
        String actual = jwtTokenProvider.createAccessToken(PAYLOAD);

        // then
        assertThat(actual.split("\\.")).hasSize(3);
    }

    @DisplayName("토큰의 Payload를 가져온다.")
    @Test
    void getPayLoadOfToken() {
        // given
        String token = jwtTokenProvider.createAccessToken(PAYLOAD);

        // when
        String actual = jwtTokenProvider.getPayLoad(token);

        // then
        assertThat(actual).isEqualTo(PAYLOAD);
    }

    @DisplayName("액세스 토큰을 검증했을 때 만료된 경우라면 예외를 던진다.")
    @Test
    void throwExceptionWhenTokenIsExpired() {
        // given
        TokenProvider expriedJwtTokenProvider = new JwtTokenProvider(JWT_SECRET_KEY, 0);
        String expiredToken = expriedJwtTokenProvider.createAccessToken(PAYLOAD);

        // when & then
        assertThatThrownBy(() -> jwtTokenProvider.validateToken(expiredToken))
                .isInstanceOf(InvalidTokenException.class);
    }

    @DisplayName("토큰을 검증하여 유효하지 않으면 예외를 던진다.")
    @Test
    void throwExceptionWhenTokenIsInValid() {
        // given
        String invalidToken = "invalid";

        // when & then
        assertThatThrownBy(() -> jwtTokenProvider.validateToken(invalidToken))
                .isInstanceOf(InvalidTokenException.class);
    }
}