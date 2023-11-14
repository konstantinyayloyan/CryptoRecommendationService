package com.xm.cryptorecommendationservice.service.crypto;


import com.xm.cryptorecommendationservice.helper.CryptoHelper;
import com.xm.cryptorecommendationservice.model.CryptoNormalizedStats;
import com.xm.cryptorecommendationservice.service.reader.CryptoReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoService {
    private final CryptoReaderService cryptoCachedCsvReaderService;

    public List<CryptoNormalizedStats> getCryptoNormalizedRangesSorted() {
        return cryptoCachedCsvReaderService.readAll()
                .entrySet()
                .stream()
                .map(entry -> new CryptoNormalizedStats(entry.getKey(), CryptoHelper.calculateNormalizedRange(entry.getValue())))
                .sorted(Comparator.comparing(CryptoNormalizedStats::normalizedRange))
                .toList();
    }
}
