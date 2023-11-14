package com.xm.cryptorecommendationservice.helper;

import com.xm.cryptorecommendationservice.exception.CryptoNotFoundException;
import com.xm.cryptorecommendationservice.model.Crypto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class CryptoHelper {
    public static LocalDateTime getOldestTimestamp(final List<Crypto> prices) {
        return prices.stream()
                .min(Comparator.comparing(Crypto::timestamp))
                .map(Crypto::timestamp)
                .orElse(null);
    }

    public static LocalDateTime getNewestTimestamp(final List<Crypto> prices) {
        return prices.stream()
                .max(Comparator.comparing(Crypto::timestamp))
                .map(Crypto::timestamp)
                .orElse(null);
    }

    public static BigDecimal getMinPrice(final List<Crypto> prices) {
        return prices.stream()
                .map(Crypto::price)
                .min(BigDecimal::compareTo)
                .orElse(null);
    }

    public static BigDecimal getMaxPrice(final List<Crypto> prices) {
        return prices.stream()
                .map(Crypto::price)
                .max(BigDecimal::compareTo)
                .orElse(null);
    }

    public static BigDecimal calculateNormalizedRange(final List<Crypto> prices) {
        final BigDecimal minPrice = getMinPrice(prices);
        final BigDecimal maxPrice = getMaxPrice(prices);

        if (minPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return maxPrice.subtract(minPrice).divide(minPrice, 4, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateNormalizedRangeForDay(List<Crypto> cryptoList, LocalDate specificDay) {
        // Filter the list to include only prices for the specific day
        List<Crypto> pricesOnSpecificDay = cryptoList.stream()
                .filter(crypto -> crypto.timestamp().toLocalDate().isEqual(specificDay))
                .toList();

        if (pricesOnSpecificDay.isEmpty()) {
            throw new CryptoNotFoundException("Crypto stats not found for the specified date");
        }

        return calculateNormalizedRange(pricesOnSpecificDay);
    }
}
