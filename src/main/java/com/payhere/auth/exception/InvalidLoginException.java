package com.payhere.auth.exception;

public class InvalidLoginException extends RuntimeException {

    public InvalidLoginException(final String message) {
        super(message);
    }
    public InvalidLoginException() {
        this("아이디 혹은 비밀번호가 잘못되었습니다.");
    }
}
