package com.xm.cryptorecommendationservice.controller;

import com.xm.cryptorecommendationservice.model.CryptoNormalizedStats;
import com.xm.cryptorecommendationservice.model.CryptoStats;
import com.xm.cryptorecommendationservice.service.crypto.CryptoService;
import com.xm.cryptorecommendationservice.service.crypto.CryptoStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cryptos")
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoService cryptoService;
    private final CryptoStatsService cryptoStatsService;

    @GetMapping("{symbolName}/stats")
    public CryptoStats getCryptoStats(@PathVariable("symbolName") final String symbolName) {
        return cryptoStatsService.getCryptoStats(symbolName);
    }

    @GetMapping("norm/stats")
    public List<CryptoNormalizedStats> getNormalizesStatsSorted() {
        return cryptoService.getCryptoNormalizedRangesSorted();
    }
}
