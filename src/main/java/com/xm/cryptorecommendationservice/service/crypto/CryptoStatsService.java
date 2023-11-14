package com.xm.cryptorecommendationservice.service.crypto;

import com.xm.cryptorecommendationservice.model.CryptoStats;
import com.xm.cryptorecommendationservice.service.reader.CryptoReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.xm.cryptorecommendationservice.helper.CryptoHelper.*;

@Service
@RequiredArgsConstructor
public class CryptoStatsService {

    private final CryptoReaderService cryptoCachedCsvReaderService;

    public CryptoStats getCryptoStats(final String cryptoSymbol) {
        final var prices = cryptoCachedCsvReaderService.readInfoOf(cryptoSymbol);
        if (prices != null && !prices.isEmpty()) {
            return new CryptoStats(
                    getOldestTimestamp(prices),
                    getNewestTimestamp(prices),
                    getMinPrice(prices),
                    getMaxPrice(prices)
            );
        }
        return null;
    }
}

