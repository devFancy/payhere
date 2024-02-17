package com.payhere.auth.dto.request;


import lombok.Builder;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "휴대폰 번호를 입력해 주세요.")
    private String cellPhoneNumber;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

    protected LoginRequest() {
    }

    @Builder
    public LoginRequest(final String cellPhoneNumber, final String password) {
        this.cellPhoneNumber = cellPhoneNumber;
        this.password = password;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
