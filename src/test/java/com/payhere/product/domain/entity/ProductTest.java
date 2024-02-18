package com.payhere.product.domain.entity;

import com.payhere.owner.domain.CellPhoneNumber;
import com.payhere.owner.domain.Password;
import com.payhere.owner.domain.entity.Owner;
import com.payhere.product.exception.InvalidProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.payhere.common.fixtures.ProductFixtures.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

    private static final int MAX_DESCRIPTION_LENGTH = 255;

    @DisplayName("신규 상품을 등록한다")
    @Test
    void saveProduct() {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));

        // when & then
        assertDoesNotThrow(() -> new Product(owner, 상품_카테고리, 상품_가격, 상품_원가, 상품_이름, 상품_설명, 상품_바코드, 상품_만기일, 상품_크기));
    }

    @DisplayName("상품 가격이 0원 이하이면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(ints = {-1000, -1, 0})
    void throwException_IfPriceIsZero_Or_Negative(final int price) {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));

        // when & then
        assertThatThrownBy(() -> new Product(owner, 상품_카테고리, price, 상품_원가, 상품_이름, 상품_설명, 상품_바코드, 상품_만기일, 상품_크기))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("상품 가격은 0원 이상이어야 합니다.");
    }

    @DisplayName("상품 원가가 0원 이하이면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(ints = {-1000, -1, 0})
    void throwException_IfCostIsZero_Or_Negative(final int cost) {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));

        // when & then
        assertThatThrownBy(() -> new Product(owner, 상품_카테고리, 상품_가격, cost, 상품_이름, 상품_설명, 상품_바코드, 상품_만기일, 상품_크기))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("상품 원가는 0원 이상이어야 합니다.");
    }

    @DisplayName("상품 설명 길이가 공백이면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void throwException_IfDescription_IsEmpty(final String description) {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));

        // when & then
        assertThatThrownBy(() -> new Product(owner, 상품_카테고리, 상품_가격, 상품_원가, 상품_이름, description, 상품_바코드, 상품_만기일, 상품_크기))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("상품 설명은 공백일 수 없습니다.");
    }

    @DisplayName("상품 설명 길이가 255를 초과하는 경우 예외를 던진다.")
    @Test
    void throwException_IfDescription_ExceedsMaxLength() {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));
        String 잘못된_상품_길이 = "1".repeat(256);

        // when & then
        assertThatThrownBy(() -> new Product(owner, 상품_카테고리, 상품_가격, 상품_원가, 상품_이름, 잘못된_상품_길이, 상품_바코드, 상품_만기일, 상품_크기))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("상품 설명의 길이는 %d를 초과할 수 없습니다.", MAX_DESCRIPTION_LENGTH);
    }
}