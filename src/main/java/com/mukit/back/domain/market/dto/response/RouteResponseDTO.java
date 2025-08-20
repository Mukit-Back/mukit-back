package com.mukit.back.domain.market.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RouteResponseDTO(
        List<List<Double>> path, // [[lat,lng], ...]
        double distance,         // 총 거리 (meters)
        double duration,         // 총 소요 시간 (seconds)
        String summary           // 요약(있으면): 예) "경부고속도로"
) {}
