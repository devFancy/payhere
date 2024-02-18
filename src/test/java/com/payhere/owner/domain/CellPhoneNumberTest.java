package com.payhere.owner.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CellPhoneNumberTest {

    @DisplayName("휴대폰 번호가 공백이거나 형식에 맞지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "01012345678", "010-12345-6789", "010-123-45678"})
    void throwException_WhenPhoneNumberIsBlank_Or_Invalid(final String cellPhoneNumber) {

        // given & when & then
        assertThatThrownBy(() -> new CellPhoneNumber(cellPhoneNumber));
    }
}