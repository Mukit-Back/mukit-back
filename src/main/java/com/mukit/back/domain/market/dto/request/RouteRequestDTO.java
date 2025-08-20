package com.mukit.back.domain.market.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import io.swagger.v3.oas.annotations.media.Schema;

@Builder
public record RouteRequestDTO(

        @Schema(description = "출발지 위도 (Latitude)", example = "37.5666805")
        @NotNull @DecimalMin("-90.0") @DecimalMax("90.0")
        Double alat,

        @Schema(description = "출발지 경도 (Longitude)", example = "126.9784147")
        @NotNull @DecimalMin("-180.0") @DecimalMax("180.0")
        Double alng,

        @Schema(description = "도착지 위도 (Latitude)", example = "37.402056")
        @NotNull @DecimalMin("-90.0") @DecimalMax("90.0")
        Double blat,

        @Schema(description = "도착지 경도 (Longitude)", example = "127.108212")
        @NotNull @DecimalMin("-180.0") @DecimalMax("180.0")
        Double blng,

        @Schema(
                description = """
                경로 탐색 우선순위 (기본값: RECOMMEND)<br>
                • RECOMMEND : 추천 경로<br>
                • TIME : 최단 시간<br>
                • DISTANCE : 최단 거리
                """,
                example = "RECOMMEND"
        )
        String priority
) {
    public String prioritySafe() {
        return switch (priority == null ? "RECOMMEND" : priority.toUpperCase()) {
            case "TIME", "DISTANCE" -> priority.toUpperCase();
            default -> "RECOMMEND";
        };
    }
}


