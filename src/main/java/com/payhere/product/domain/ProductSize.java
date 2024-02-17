package com.payhere.product.domain;

import com.payhere.product.exception.InvalidProductException;
import lombok.Getter;

@Getter
public enum ProductSize {
    SMALL("작은 사이즈"),
    LARGE("큰 사이즈");

    private final String text;

    ProductSize(final String text) {
        this.text = text;
    }

    public static ProductSize from(final String text) {
        try {
            return ProductSize.valueOf(text.toUpperCase());
        } catch (final IllegalArgumentException e) {
            throw new InvalidProductException("(" + text + ")는 존재하지 않는 상품 사이즈입니다.");
        }
    }
}
