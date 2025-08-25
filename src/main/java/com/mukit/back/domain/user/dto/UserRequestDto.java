package com.mukit.back.domain.user.dto;

import com.mukit.back.domain.user.entity.FullLevel;
import com.mukit.back.domain.user.entity.HumanLevel;
import com.mukit.back.domain.user.entity.MarketType;
import com.mukit.back.domain.user.entity.SpicyLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRequestDto {

    @Schema(example = "TONGIN", description = "시장 타입 (예: TONGIN, MANGWON, NAMDAEMUN)")
    private MarketType marketType;

    @Schema(example = "SOLO", description = "혼잡도 (예: SOLO, COUPLE, GROUP)")
    private HumanLevel humanLevel;

    @Schema(example = "HOT", description = "선호 매운맛 정도 (예: NONE, MILD, MEDIUM, HOT)")
    private SpicyLevel spicyLevel;

    @Schema(example = "FULL", description = "배부름 정도 (예: LIGHT, NORMAL, FULL)")
    private FullLevel fullLevel;
}
