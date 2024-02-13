package com.payhere.owner.exception;

public class InvalidCellPhoneNumberException extends RuntimeException {

    public InvalidCellPhoneNumberException(final String message) {
        super(message);
    }

    public InvalidCellPhoneNumberException() {
        this("올바른 휴대폰 번호 형식이 아닙니다.");
    }
}
