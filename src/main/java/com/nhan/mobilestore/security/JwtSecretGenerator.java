package com.nhan.mobilestore.security;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretBytes = new byte[64]; // 64 bytes for the secret
        secureRandom.nextBytes(secretBytes);
        String secret = Base64.getEncoder().encodeToString(secretBytes);
        System.out.println("Generated JWT Secret: " + secret);
    }
}
