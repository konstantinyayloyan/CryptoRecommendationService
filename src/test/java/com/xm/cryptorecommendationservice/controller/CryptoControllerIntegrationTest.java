package com.xm.cryptorecommendationservice.controller;

import com.xm.cryptorecommendationservice.model.CryptoStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CryptoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getCryptoStats_Successful() {
        String symbolName = "BTC";

        CryptoStats response = restTemplate.getForObject("/cryptos/{symbolName}/stats", CryptoStats.class, symbolName);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(24316.61), response.minPrice());
        assertEquals(BigDecimal.valueOf(46979.61), response.maxPrice());
    }

    @Test
    void getCryptoStats_Fail() {
        final String symbolName = "UNKNOWN";
        final var responseEntity = restTemplate.getForEntity("/cryptos/{symbolName}/stats", String.class, symbolName);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}

