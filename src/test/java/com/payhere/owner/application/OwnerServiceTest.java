package com.payhere.owner.application;

import com.payhere.global.config.JpaAuditingConfig;
import com.payhere.owner.domain.OwnerRepository;
import com.payhere.owner.dto.SignupRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import static com.payhere.common.fixtures.OwnerFixtures.사장님_비밀번호;
import static com.payhere.common.fixtures.OwnerFixtures.사장님_휴대폰_번호;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(JpaAuditingConfig.class)
@Profile("test")
@SpringBootTest
class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerRepository ownerRepository;

    @AfterEach
    void tearDown() {
        ownerRepository.deleteAllInBatch();
    }

    @DisplayName("사장님은 휴대폰 번호와 비밀번호를 통해 회원가입을 할 수 있다.")
    @Test
    void signup() {
        // given
        SignupRequest request = SignupRequest.builder()
                .cellPhoneNumber(사장님_휴대폰_번호)
                .password(사장님_비밀번호)
                .build();

        // when
        Long ownerId = ownerService.signUp(request);

        // then
        assertAll(
                () -> assertThat(ownerId).isNotNull(),
                () -> assertEquals(request.getCellPhoneNumber(), request.getCellPhoneNumber()),
                () -> assertEquals(request.getPassword(), request.getPassword())
        );
    }
}