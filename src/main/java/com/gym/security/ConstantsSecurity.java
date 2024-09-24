package com.gym.security;

import java.util.Base64;

public class ConstantsSecurity {
    public static final long JWT_EXPIRATION_TOKEN = 300000;
    public static final String JWT_SIGNATURE_KEY = Base64.getEncoder().encodeToString("your-secure-secret-key-which-is-long-enough".getBytes());

}
