package com.payhere.auth.exception;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException(final String message) {
        super(message);
    }
    public LoginFailedException() {
        this("아이디 혹은 비밀번호가 잘못되었습니다.");
    }
}
