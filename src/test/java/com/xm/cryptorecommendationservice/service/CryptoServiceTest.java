package com.xm.cryptorecommendationservice.service;

import com.xm.cryptorecommendationservice.model.Crypto;
import com.xm.cryptorecommendationservice.service.crypto.CryptoService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptoServiceTest {

    @Mock
    private CryptoReaderService cryptoReaderService;

    @InjectMocks
    private CryptoService cryptoService;

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

        when(cryptoReaderService.readAll()).thenReturn(mockCryptoPrices);

        final var cryptoNormalizedRanges = cryptoService.getCryptoNormalizedRangesSorted();

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

