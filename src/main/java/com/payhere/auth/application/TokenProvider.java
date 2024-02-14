package com.payhere.auth.application;

public interface TokenProvider {

    String createAccessToken(final String payLoad);

    void validateToken(final String accessToken);

    String getPayLoad(final String accessToken);
}
