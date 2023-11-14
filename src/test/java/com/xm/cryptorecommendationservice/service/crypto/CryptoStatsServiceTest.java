package com.xm.cryptorecommendationservice.service.crypto;

import com.xm.cryptorecommendationservice.model.Crypto;
import com.xm.cryptorecommendationservice.model.CryptoStats;
import com.xm.cryptorecommendationservice.service.reader.CryptoReaderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CryptoStatsServiceTest {

    @Mock
    private CryptoReaderService cryptoReaderService;

    @InjectMocks
    private CryptoStatsService cryptoStatsService;

    @Test
    void getCryptoStats_Successful() {
        String cryptoSymbol = "BTC";
        List<Crypto> mockPrices = Arrays.asList(
                new Crypto(LocalDateTime.parse("2023-11-15T12:00:00"), "BTC", new BigDecimal("50000")),
                new Crypto(LocalDateTime.parse("2023-11-16T12:00:00"), "BTC", new BigDecimal("51000"))
        );

        when(cryptoReaderService.readInfoOf(cryptoSymbol)).thenReturn(mockPrices);

        CryptoStats result = cryptoStatsService.getCryptoStats(cryptoSymbol);

        assertNotNull(result);
        assertEquals(LocalDateTime.parse("2023-11-15T12:00:00"), result.oldestTimestamp());
        assertEquals(LocalDateTime.parse("2023-11-16T12:00:00"), result.newestTimestamp());
        assertEquals(new BigDecimal("50000"), result.minPrice());
        assertEquals(new BigDecimal("51000"), result.maxPrice());

        verify(cryptoReaderService, times(1)).readInfoOf(cryptoSymbol);
        verifyNoMoreInteractions(cryptoReaderService);
    }

    @Test
    void getCryptoStats_EmptyList() {
        String cryptoSymbol = "BTC";

        when(cryptoReaderService.readInfoOf(cryptoSymbol)).thenReturn(null);

        CryptoStats result = cryptoStatsService.getCryptoStats(cryptoSymbol);

        assertNull(result);

        verify(cryptoReaderService, times(1)).readInfoOf(cryptoSymbol);
        verifyNoMoreInteractions(cryptoReaderService);
    }

    @Test
    void getCryptoNormalizedRanges_Successful() {
        final String btcSymbol = "BTC";
        final String ethSymbol = "ETH";

        final var btcPrices = Arrays.asList(
                new Crypto(LocalDateTime.now(), btcSymbol, BigDecimal.valueOf(50000)),
                new Crypto(LocalDateTime.now(), btcSymbol, BigDecimal.valueOf(51000))
        );

        final var ethPrices = Arrays.asList(
                new Crypto(LocalDateTime.now(), ethSymbol, BigDecimal.valueOf(2000)),
                new Crypto(LocalDateTime.now(), ethSymbol, BigDecimal.valueOf(2100))
        );

        final Map<String, List<Crypto>> mockCryptoPrices = new HashMap<>();
        mockCryptoPrices.put(btcSymbol, btcPrices);
        mockCryptoPrices.put(ethSymbol, ethPrices);

        when(cryptoReaderService.readInfoOfAll()).thenReturn(mockCryptoPrices);

        final var cryptoNormalizedRanges = cryptoStatsService.getCryptoNormalizedRangesSorted();

        assertNotNull(cryptoNormalizedRanges);
        assertFalse(cryptoNormalizedRanges.isEmpty());

        final var btc = cryptoNormalizedRanges.get(0);
        final var eth = cryptoNormalizedRanges.get(1);

        assertEquals(btc.symbol(), btcSymbol);
        assertEquals(eth.symbol(), ethSymbol);
        assertEquals(new BigDecimal("0.02").setScale(4, RoundingMode.HALF_UP), btc.normalizedRange());
        assertEquals(new BigDecimal("0.05").setScale(4, RoundingMode.HALF_UP), eth.normalizedRange());
    }
}

