package com.payhere.auth.application;

public interface TokenProvider {

    String createAccessToken(final String payLoad);
}
