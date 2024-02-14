package com.payhere.auth.dto;

import lombok.Getter;

@Getter
public class LoginOwner {

    private final Long id;


    public LoginOwner(final Long id) {
        this.id = id;
    }
}
