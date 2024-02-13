package com.payhere.owner.domain;

import com.payhere.owner.exception.InvalidCellPhoneNumberException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
public class CellPhoneNumber {

    private static final Pattern PATTERN = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");

    @Column(name = "cell_phone_number")
    private String value;

    protected CellPhoneNumber() {
    }

    public CellPhoneNumber(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidCellPhoneNumberException("휴대폰 번호를 입력해 주세요.");
        }
        if (!PATTERN.matcher(value).matches()) {
            throw new InvalidCellPhoneNumberException();
        }
    }

    public String getValue() {
        return value;
    }
}
