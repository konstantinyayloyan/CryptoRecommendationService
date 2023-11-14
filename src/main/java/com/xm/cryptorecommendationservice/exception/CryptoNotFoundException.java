package com.xm.cryptorecommendationservice.exception;

public class CryptoNotFoundException extends RuntimeException {

    public CryptoNotFoundException(final String message) {
        super(message);
    }
}
