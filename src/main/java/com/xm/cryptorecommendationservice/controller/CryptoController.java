package com.xm.cryptorecommendationservice.controller;

import com.xm.cryptorecommendationservice.model.CryptoNormalizedStats;
import com.xm.cryptorecommendationservice.model.CryptoStats;
import com.xm.cryptorecommendationservice.service.crypto.CryptoStatsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("cryptos")
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoStatsService cryptoStatsService;

    @GetMapping("{symbolName}/stats")
    @Operation(description = "Api for getting stats of crypto based on symbol name")
    public CryptoStats getCryptoStats(@PathVariable("symbolName") final String symbolName) {
        return cryptoStatsService.getCryptoStats(symbolName);
    }

    @GetMapping("stats/normalized-sorted")
    @Operation(description = "Api for getting sorted stats of each crypto with it's normalized price")
    public List<CryptoNormalizedStats> getNormalizesStatsSorted() {
        return cryptoStatsService.getCryptoNormalizedRangesSorted();
    }

    @GetMapping("stats/highest-range/{date}")
    @Operation(description = "Api for getting crypto with highest normalized range for date")
    public CryptoNormalizedStats getCryptoWithHighestNormalizedRangeForDate(
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return cryptoStatsService.getCryptoWithHighestNormalizedRangeForDay(date);
    }
}
