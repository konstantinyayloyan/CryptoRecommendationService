package com.xm.cryptorecommendationservice.service.reader;

import com.xm.cryptorecommendationservice.model.Crypto;

import java.util.List;
import java.util.Map;

public interface CryptoReaderService {

    Map<String, List<Crypto>> readInfoOfAll();
    List<Crypto> readInfoOf(String cryptoSymbol);
}
