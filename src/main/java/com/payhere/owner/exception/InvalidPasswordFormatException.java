package com.payhere.owner.exception;

public class InvalidPasswordFormatException extends RuntimeException {

    public InvalidPasswordFormatException(final String message) {
        super(message);
    }

    public InvalidPasswordFormatException() {
        this("올바른 비밀번호 형식이 아닙니다.");
    }
}
