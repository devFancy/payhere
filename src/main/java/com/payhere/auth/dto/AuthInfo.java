package com.payhere.auth.dto;

import lombok.Getter;

@Getter
public class AuthInfo {

    private final Long id;

    private final String cellPhoneNumber;

    public AuthInfo(final Long id, final String cellPhoneNumber) {
        this.id = id;
        this.cellPhoneNumber = cellPhoneNumber;
    }
}
