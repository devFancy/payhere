package com.payhere.auth.domain.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing implements HashingI {

    protected Hashing() {
    }

    @Override
    public String generateSHA256Hash(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(text.getBytes());
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
