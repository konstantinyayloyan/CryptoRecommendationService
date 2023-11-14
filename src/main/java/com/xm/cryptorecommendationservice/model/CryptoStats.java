package com.xm.cryptorecommendationservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CryptoStats(LocalDateTime oldestTimestamp, LocalDateTime newestTimestamp,
                          BigDecimal minPrice, BigDecimal maxPrice) {
}
