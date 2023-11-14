package com.xm.cryptorecommendationservice.exception;

public class RateLimitedException extends RuntimeException {
    public RateLimitedException(final String message) {
        super(message);
    }
}
