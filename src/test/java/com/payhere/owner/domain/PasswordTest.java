package com.payhere.owner.domain;

import com.payhere.auth.domain.HashingFactory;
import com.payhere.auth.domain.HashingI;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    @DisplayName("사장님의 비밀번호는 영어 대소문자, 숫자, 특수문자(@$!%*#?&) 세 카테고리를 모두 포함하는 8자이상 20자 이하의 문자열이 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "AbCdEfG", "AbCD1234", "12347890", "12347890!!", "qwer!", "qwer1234",
            "123456789012345678901"})
    void owner_password_exception_format(final String invalidPassword) {
        // given
        HashingI hashing = new HashingFactory().getHashing();

        // when & then
        Assertions.assertThatThrownBy(() -> Password.of(hashing, invalidPassword));
    }
}