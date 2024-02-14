package com.payhere.auth.domain.hashing;

public interface HashingI {

    String generateSHA256Hash(String text);
}
