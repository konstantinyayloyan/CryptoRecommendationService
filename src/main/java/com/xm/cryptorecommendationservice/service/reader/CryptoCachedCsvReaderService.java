package com.xm.cryptorecommendationservice.service.reader;

import com.xm.cryptorecommendationservice.model.Crypto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class CryptoCachedCsvReaderService implements CryptoReaderService {

    private final CryptoReaderService cryptoCsvReaderService;

    @Override
    @Cacheable("cryptos")
    public Map<String, List<Crypto>> readInfoOfAll() {
        log.info("Info of all cryptos are not cached yet, so reading from file");
        return cryptoCsvReaderService.readInfoOfAll();
    }

    @Override
    @Cacheable("crypto")
    public List<Crypto> readInfoOf(final String cryptoSymbol) {
        log.info("Info of {} is not cached yet, so reading it from file", cryptoSymbol);
        return cryptoCsvReaderService.readInfoOf(cryptoSymbol);
    }

    @CacheEvict(value = "cryptos", allEntries = true)
    @Scheduled(fixedDelayString = "${cache.eviction.time.hours:2}", timeUnit = TimeUnit.HOURS)
    public void evictAllCryptosCache() {
        log.debug("Evicting cache holding all cryptos");
    }

    @CacheEvict(value = "crypto", allEntries = true)
    @Scheduled(fixedDelayString = "${cache.eviction.time.hours:2}", timeUnit = TimeUnit.HOURS)
    public void evictAllCryptoCache() {
        log.debug("Evicting cache holding all cryptos by symbol name");
    }
}
