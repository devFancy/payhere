package com.payhere.auth.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(final String message) {
        super(message);
    }
    public InvalidTokenException() {
        this("유효하지 않는 토큰입니다.");
    }
}
