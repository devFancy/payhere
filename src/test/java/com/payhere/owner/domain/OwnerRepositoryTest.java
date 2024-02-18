package com.payhere.owner.domain;

import com.payhere.global.config.JpaAuditingConfig;
import com.payhere.owner.domain.entity.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(JpaAuditingConfig.class)
@ActiveProfiles("test")
@DataJpaTest
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));
        ownerRepository.save(owner);
    }

    @DisplayName("회원가입한 휴대폰 번호와 비밀번호를 DB로부터 정상적으로 가져온다.")
    @Test
    void findByCellPhoneNumberAndPassword() {
        // given
        String cellPhoneNumber = owner.getCellPhoneNumber();
        String password = owner.getPassword();

        // when
        Optional<Owner> foundOwner = ownerRepository.findByCellPhoneNumberAndPassword(cellPhoneNumber, password);

        // then
        assertThat(foundOwner).isPresent();
        assertThat(foundOwner.get().getCellPhoneNumber()).isEqualTo(cellPhoneNumber);
        assertThat(foundOwner.get().getPassword()).isEqualTo(password);
    }

}