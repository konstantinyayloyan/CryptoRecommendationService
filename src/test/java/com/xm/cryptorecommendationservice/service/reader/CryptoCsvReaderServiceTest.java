package com.xm.cryptorecommendationservice.service.reader;


import com.xm.cryptorecommendationservice.exception.CryptoNotFoundException;
import com.xm.cryptorecommendationservice.model.Crypto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CryptoCsvReaderServiceTest {

    @Autowired
    private CryptoReaderService cryptoCsvReaderService;

    @Test
    void readPrices_Successful() {
        String cryptoSymbol = "BTC";
        List<Crypto> prices = cryptoCsvReaderService.readInfoOf(cryptoSymbol);

        assertNotNull(prices);
        assertEquals(3, prices.size());
        assertEquals(LocalDateTime.parse("2022-01-01T08:00", DateTimeFormatter.ISO_DATE_TIME), prices.get(0).timestamp());
        assertEquals("BTC", prices.get(0).symbol());
        assertEquals(new BigDecimal("46813.21"), prices.get(0).price());
        assertEquals(LocalDateTime.parse("2022-01-01T11:00", DateTimeFormatter.ISO_DATE_TIME), prices.get(1).timestamp());
        assertEquals("BTC", prices.get(1).symbol());
        assertEquals(new BigDecimal("46979.61"), prices.get(1).price());
    }

    @Test
    void readPrices_FileNotFound() {
        String cryptoSymbol = "UNKNOWN";
        CryptoNotFoundException cryptoNotFoundException =
                assertThrows(CryptoNotFoundException.class, () -> cryptoCsvReaderService.readInfoOf(cryptoSymbol));

        assertEquals(cryptoNotFoundException.getMessage(), "File not found with symbol name " + cryptoSymbol);
    }

    @Test
    void readAll_Successful() {
        Map<String, List<Crypto>> allPrices = cryptoCsvReaderService.readInfoOfAll();

        assertNotNull(allPrices);
        assertFalse(allPrices.isEmpty());

        // Assuming you have BTC_values.csv and ETH_values.csv in the specified folder
        assertTrue(allPrices.containsKey("BTC"));
        assertTrue(allPrices.containsKey("ETH"));

        // Check if prices are read for each crypto
        List<Crypto> btcPrices = allPrices.get("BTC");
        assertNotNull(btcPrices);
        assertFalse(btcPrices.isEmpty());
        assertEquals(btcPrices.size(), 3);


        List<Crypto> ethPrices = allPrices.get("ETH");
        assertNotNull(ethPrices);
        assertFalse(ethPrices.isEmpty());
        assertEquals(ethPrices.size(), 3);
    }
}


