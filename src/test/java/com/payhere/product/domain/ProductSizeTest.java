package com.payhere.product.domain;

import com.payhere.product.exception.InvalidProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductSizeTest {

    @DisplayName("상품 사이즈를 가져온다.")
    @ParameterizedTest
    @EnumSource
    void getProductSize(final ProductSize productSize) {
        // given & when & then
        assertAll(() -> {
            assertThat(ProductSize.from(productSize.name())).isEqualTo(productSize);
            assertThat(ProductSize.from(productSize.name().toLowerCase())).isEqualTo(productSize);
        });
    }

    @DisplayName("상품 사이즈가 SMALL 혹은 LARGE가 아닌경우 예외를 던진다.")
    @Test
    void throwExceptionIfProductSizeIsNotSmallOrLarge() {
        // given
        String notFoundProductSize = "존재하지 않는 상품 사이즈";

        // when & then
        assertThatThrownBy(() ->  ProductSize.from(notFoundProductSize))
                .isInstanceOf(InvalidProductException.class);
    }
}