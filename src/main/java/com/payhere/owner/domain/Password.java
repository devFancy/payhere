package com.payhere.owner.domain;

import com.payhere.owner.exception.InvalidCellPhoneNumberException;
import com.payhere.owner.exception.InvalidPasswordFormatException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
public class Password {

    private static final Pattern PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$");

    @Column(name = "password")
    private String value;

    protected Password() {
    }

    public Password(final String value) {
        validate(value);
        this.value = value;
    }


    private static void validate(final String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidPasswordFormatException("비밀번호를 입력해 주세요.");
        }
        if (!PATTERN.matcher(value).matches()) {
            throw new InvalidPasswordFormatException();
        }
    }
}
