package com.xm.cryptorecommendationservice.service.reader;

import com.xm.cryptorecommendationservice.model.Crypto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CryptoCachedCsvReaderService implements CryptoReaderService {

    private final CryptoReaderService cryptoCsvReaderService;


    @Override
    @Cacheable("cryptos")
    public Map<String, List<Crypto>> readAll() {
        return cryptoCsvReaderService.readAll();
    }

    @Override
    @Cacheable("crypto")
    public List<Crypto> readPrices(String cryptoSymbol) {
        return cryptoCsvReaderService.readPrices(cryptoSymbol);
    }
}
