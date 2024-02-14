package com.payhere.product.exception;

public class NotFoundProductException extends RuntimeException {
    public NotFoundProductException(final String message) {
        super(message);
    }

    public NotFoundProductException() {
        this("존재하지 않는 상품입니다.");
    }
}
