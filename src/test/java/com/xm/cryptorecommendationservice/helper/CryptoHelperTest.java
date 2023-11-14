package com.xm.cryptorecommendationservice.helper;

import com.xm.cryptorecommendationservice.exception.CryptoNotFoundException;
import com.xm.cryptorecommendationservice.model.Crypto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CryptoHelperTest {

    @Test
    void getOldestTimestamp_WithValidData_ReturnsOldestTimestamp() {
        List<Crypto> prices = createMockCryptoList();
        LocalDateTime oldestTimestamp = CryptoHelper.getOldestTimestamp(prices);
        assertNotNull(oldestTimestamp);
        assertEquals(LocalDateTime.parse("2023-01-01T00:00:00"), oldestTimestamp);
    }

    @Test
    void getOldestTimestamp_WithValidData_ReturnsNewestTimestamp() {
        List<Crypto> prices = createMockCryptoList();
        LocalDateTime newestTimestamp = CryptoHelper.getNewestTimestamp(prices);
        assertNotNull(newestTimestamp);
        assertEquals(LocalDateTime.parse("2023-01-05T00:00:00"), newestTimestamp);
    }

    @Test
    void getOldestTimestamp_WithValidData_ReturnsMinPrice() {
        List<Crypto> prices = createMockCryptoList();
        BigDecimal maxPrice = CryptoHelper.getMaxPrice(prices);
        assertEquals(maxPrice, BigDecimal.valueOf(140));
    }

    @Test
    void calculateNormalizedRange_WithValidData_ReturnsNormalizedRange() {
        List<Crypto> prices = createMockCryptoList();
        BigDecimal normalizedRange = CryptoHelper.calculateNormalizedRange(prices);
        assertNotNull(normalizedRange);
        assertEquals(new BigDecimal("0.4").setScale(4, RoundingMode.HALF_UP), normalizedRange);
    }

    @Test
    void calculateNormalizedRange_WithZeroMinPrice_ReturnsZero() {
        List<Crypto> prices = createMockCryptoListWithZeroMinPrice();
        BigDecimal normalizedRange = CryptoHelper.calculateNormalizedRange(prices);
        assertNotNull(normalizedRange);
        assertEquals(BigDecimal.ZERO, normalizedRange);
    }

    @Test
    void calculateNormalizedRangeForDay_WithValidData_ReturnsNormalizedRange() {
        List<Crypto> prices = createMockCryptoListWithSameDate();
        LocalDate specificDay = LocalDate.parse("2023-01-05");
        BigDecimal normalizedRange = CryptoHelper.calculateNormalizedRangeForDay(prices, specificDay);
        assertNotNull(normalizedRange);
        assertEquals(new BigDecimal("0.4").setScale(4, RoundingMode.HALF_UP), normalizedRange);
    }

    @Test
    void calculateNormalizedRangeForDay_WithNoDataForDay_ThrowsCryptoNotFoundException() {
        List<Crypto> prices = createMockCryptoList();
        LocalDate specificDay = LocalDate.parse("2025-01-01");
        assertThrows(CryptoNotFoundException.class, () -> CryptoHelper.calculateNormalizedRangeForDay(prices, specificDay));
    }

    private List<Crypto> createMockCryptoList() {
        List<Crypto> prices = new ArrayList<>();
        prices.add(createMockCrypto("BTC", "2023-01-01T00:00:00", BigDecimal.valueOf(100)));
        prices.add(createMockCrypto("BTC", "2023-01-02T00:00:00", BigDecimal.valueOf(110)));
        prices.add(createMockCrypto("BTC", "2023-01-03T00:00:00", BigDecimal.valueOf(120)));
        prices.add(createMockCrypto("BTC", "2023-01-04T00:00:00", BigDecimal.valueOf(130)));
        prices.add(createMockCrypto("BTC", "2023-01-05T00:00:00", BigDecimal.valueOf(140)));
        return prices;
    }

    private List<Crypto> createMockCryptoListWithSameDate() {
        List<Crypto> prices = new ArrayList<>();
        prices.add(createMockCrypto("BTC", "2023-01-05T00:00:00", BigDecimal.valueOf(100)));
        prices.add(createMockCrypto("BTC", "2023-01-02T00:00:00", BigDecimal.valueOf(110)));
        prices.add(createMockCrypto("BTC", "2023-01-03T00:00:00", BigDecimal.valueOf(120)));
        prices.add(createMockCrypto("BTC", "2023-01-05T00:00:00", BigDecimal.valueOf(130)));
        prices.add(createMockCrypto("BTC", "2023-01-05T00:00:00", BigDecimal.valueOf(140)));


        prices.add(createMockCrypto("ETH", "2023-01-05T00:00:00", BigDecimal.valueOf(100)));
        prices.add(createMockCrypto("ETH", "2023-01-02T00:00:00", BigDecimal.valueOf(110)));
        prices.add(createMockCrypto("ETH", "2023-01-05T00:00:00", BigDecimal.valueOf(120)));
        prices.add(createMockCrypto("ETH", "2023-01-04T00:00:00", BigDecimal.valueOf(130)));
        prices.add(createMockCrypto("ETH", "2023-01-05T00:00:00", BigDecimal.valueOf(140)));
        return prices;
    }

    private List<Crypto> createMockCryptoListWithZeroMinPrice() {
        List<Crypto> prices = new ArrayList<>();
        prices.add(createMockCrypto("BTC", "2023-01-01T00:00:00", BigDecimal.ZERO));
        prices.add(createMockCrypto("BTC", "2023-01-02T00:00:00", BigDecimal.valueOf(110)));
        prices.add(createMockCrypto("BTC", "2023-01-03T00:00:00", BigDecimal.valueOf(120)));
        prices.add(createMockCrypto("BTC", "2023-01-04T00:00:00", BigDecimal.valueOf(130)));
        prices.add(createMockCrypto("BTC", "2023-01-05T00:00:00", BigDecimal.valueOf(140)));
        return prices;
    }

    private Crypto createMockCrypto(String symbol, String timestamp, BigDecimal price) {
        Crypto crypto = mock(Crypto.class);
        when(crypto.symbol()).thenReturn(symbol);
        when(crypto.timestamp()).thenReturn(LocalDateTime.parse(timestamp));
        when(crypto.price()).thenReturn(price);
        return crypto;
    }
}

