package com.payhere.owner.exception;

public class NotFoundOwnerException extends RuntimeException {
    public NotFoundOwnerException(final String message) {
        super(message);
    }
    public NotFoundOwnerException() {
        this("존재하지 않는 사장님 회원입니다.");
    }
}
