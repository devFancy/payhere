package com.payhere.auth.domain;

public interface HashingI {

    String generateSHA256Hash(String text);
}
