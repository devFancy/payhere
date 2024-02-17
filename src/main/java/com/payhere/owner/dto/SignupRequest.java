package com.payhere.owner.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupRequest {

    private String cellPhoneNumber;
    private String password;

    protected SignupRequest() {
    }

    @Builder
    public SignupRequest(final String cellPhoneNumber, final String password) {
        this.cellPhoneNumber = cellPhoneNumber;
        this.password = password;
    }
}
