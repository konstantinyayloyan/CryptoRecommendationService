package com.xm.cryptorecommendationservice.model;

import java.math.BigDecimal;

public record CryptoNormalizedStats(String symbol, BigDecimal normalizedRange) {
}
