package com.payhere.auth.domain.entity;

import com.payhere.owner.domain.entity.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.payhere.common.fixtures.OwnerFixtures.사장님;

class AuthTokenTest {

    @DisplayName("AccessToken을 생성한다.")
    @Test
    void createAccessToken() {
        // given
        Owner 사장님 = 사장님();
        String accessToken = "accessToken";

        // when & then
        Assertions.assertDoesNotThrow(() -> new AuthToken(사장님, accessToken));
     }
}