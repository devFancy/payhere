package com.payhere.owner.domain.entity;

import com.payhere.owner.domain.CellPhoneNumber;
import com.payhere.owner.domain.Password;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class OwnerTest {

    @DisplayName("회원을 생성한다.")
    @Test
    void 회원을_생성한다() {
        // given
        CellPhoneNumber cellPhoneNumber = new CellPhoneNumber("010-1234-5678");
        Password password = new Password("Qwer1234!!");

        // given & when & then
        assertDoesNotThrow(() -> new Owner(cellPhoneNumber, password));
    }
}