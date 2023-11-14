package com.xm.cryptorecommendationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CryptoRecommendationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoRecommendationServiceApplication.class, args);
    }

}
