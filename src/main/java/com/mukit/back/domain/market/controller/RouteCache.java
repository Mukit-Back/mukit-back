package com.mukit.back.domain.market.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mukit.back.domain.market.dto.response.RouteResponseDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Supplier;

@Component
public class RouteCache {

    private final Cache<String, RouteResponseDTO> cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(5))
            .maximumSize(10_000)
            .build();

    public RouteResponseDTO getOrPut(String key, Supplier<RouteResponseDTO> supplier) {
        RouteResponseDTO existing = cache.getIfPresent(key);
        if (existing != null) {
            return existing;
        }
        RouteResponseDTO value = supplier.get();
        cache.put(key, value);
        return value;
    }
}

