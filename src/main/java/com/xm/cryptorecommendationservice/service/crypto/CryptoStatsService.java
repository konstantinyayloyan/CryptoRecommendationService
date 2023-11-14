package com.xm.cryptorecommendationservice.service.crypto;

import com.xm.cryptorecommendationservice.helper.CryptoHelper;
import com.xm.cryptorecommendationservice.model.CryptoNormalizedStats;
import com.xm.cryptorecommendationservice.model.CryptoStats;
import com.xm.cryptorecommendationservice.service.reader.CryptoReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

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

    public List<CryptoNormalizedStats> getCryptoNormalizedRangesSorted() {
        return cryptoCachedCsvReaderService.readInfoOfAll()
                .entrySet()
                .stream()
                .map(entry -> new CryptoNormalizedStats(entry.getKey(), CryptoHelper.calculateNormalizedRange(entry.getValue())))
                .sorted(Comparator.comparing(CryptoNormalizedStats::normalizedRange))
                .toList();
    }

    public CryptoNormalizedStats getCryptoWithHighestNormalizedRangeForDay(final LocalDate specificDay) {
        return cryptoCachedCsvReaderService.readInfoOfAll()
                .entrySet()
                .stream()
                .map(entry -> new CryptoNormalizedStats(entry.getKey(), CryptoHelper.calculateNormalizedRangeForDay(entry.getValue(), specificDay)))
                .max(Comparator.comparing(CryptoNormalizedStats::normalizedRange))
                .orElse(null);
    }
}

