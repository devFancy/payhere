package com.payhere.product.exception;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException(final String message) {
        super(message);
    }
    public InvalidProductException() {
        this("잘못된 상품입니다.");
    }
}
