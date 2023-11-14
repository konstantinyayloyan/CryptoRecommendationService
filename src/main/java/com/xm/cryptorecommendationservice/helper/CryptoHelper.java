package com.xm.cryptorecommendationservice.helper;

import com.xm.cryptorecommendationservice.model.Crypto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class CryptoHelper {
    public static LocalDateTime getOldestTimestamp(List<Crypto> prices) {
        return prices.stream()
                .min(Comparator.comparing(Crypto::timestamp))
                .map(Crypto::timestamp)
                .orElse(null);
    }

    public static LocalDateTime getNewestTimestamp(List<Crypto> prices) {
        return prices.stream()
                .max(Comparator.comparing(Crypto::timestamp))
                .map(Crypto::timestamp)
                .orElse(null);
    }

    public static BigDecimal getMinPrice(List<Crypto> prices) {
        return prices.stream()
                .map(Crypto::price)
                .min(BigDecimal::compareTo)
                .orElse(null);
    }

    public static BigDecimal getMaxPrice(List<Crypto> prices) {
        return prices.stream()
                .map(Crypto::price)
                .max(BigDecimal::compareTo)
                .orElse(null);
    }

    public static BigDecimal calculateNormalizedRange(List<Crypto> prices) {
        BigDecimal minPrice = getMinPrice(prices);
        BigDecimal maxPrice = getMaxPrice(prices);

        if (minPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return maxPrice.subtract(minPrice).divide(minPrice, 4, RoundingMode.HALF_UP);
    }
}