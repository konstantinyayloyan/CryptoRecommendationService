package com.xm.cryptorecommendationservice.rate.limiting;

import com.xm.cryptorecommendationservice.exception.RateLimitedException;
import com.xm.cryptorecommendationservice.utils.IpAddressUtil;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitingAspect {

    private final Map<String, Bucket> ipBucketMap = new ConcurrentHashMap<>();

    @Around("@annotation(com.xm.cryptorecommendationservice.rate.limiting.RateLimited)")
    public Object enforceRateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        String ipAddress = IpAddressUtil.getClientIpAddress(); // Implement a method to retrieve the IP address

        // Create or get the bucket for the IP address
        Bucket bucket = ipBucketMap.computeIfAbsent(ipAddress, k -> Bucket.builder().addLimit(getBandwidth()).build());

        if (bucket.tryConsume(1)) {
            return joinPoint.proceed();
        } else {
            throw new RateLimitedException("Rate limit exceeded");
        }
    }

    private Bandwidth getBandwidth() {
        return Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
    }
}

