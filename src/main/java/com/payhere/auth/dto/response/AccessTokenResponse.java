package com.payhere.auth.dto.response;

public class AccessTokenResponse {

    private final String accessToken;

    public AccessTokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
