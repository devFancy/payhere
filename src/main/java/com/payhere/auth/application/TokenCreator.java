package com.payhere.auth.application;

import com.payhere.auth.domain.AuthAccessToken;

public interface TokenCreator {
    AuthAccessToken createAuthToken(final Long ownerId);

    Long extractPayLoad(final String accessToken);
}
