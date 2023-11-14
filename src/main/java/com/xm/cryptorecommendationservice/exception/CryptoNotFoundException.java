package com.xm.cryptorecommendationservice.exception;

public class CryptoNotFoundException extends RuntimeException {

    public CryptoNotFoundException(String message) {
        super(message);
    }
}
