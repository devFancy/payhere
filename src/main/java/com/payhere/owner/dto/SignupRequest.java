package com.payhere.owner.dto;

import lombok.Getter;

@Getter
public class SignupRequest {

    private String cellPhoneNumber;
    private String password;

    protected SignupRequest() {
    }

    public SignupRequest(final String cellPhoneNumber, final String password) {
        this.cellPhoneNumber = cellPhoneNumber;
        this.password = password;
    }
}
