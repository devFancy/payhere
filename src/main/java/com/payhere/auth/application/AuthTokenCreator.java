package com.payhere.auth.application;

import com.payhere.auth.domain.AuthAccessToken;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenCreator implements TokenCreator{

    private final TokenProvider tokenProvider;

    public AuthTokenCreator(final TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public AuthAccessToken createAuthToken(final Long ownerId) {
        String accessToken = tokenProvider.createAccessToken(String.valueOf(ownerId));

        return new AuthAccessToken(accessToken);
    }
}
