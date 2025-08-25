package com.mukit.back.domain.market.dto.request;


import com.mukit.back.domain.market.entity.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CourseRequestDTO {

    public record Candidate(

            @Schema(example = "1", description = "가게 ID")
            Long shopId,

            @Schema(example = "김밥천국 강남점", description = "가게 이름")
            String name,

            @Schema(example = "[\"김밥\", \"라면\", \"떡볶이\"]", description = "메뉴 리스트")
            List<String> menus,

            @Schema(example = "KOREAN", description = "카테고리 (예: KOREAN, JAPANESE, CHINESE 등)")
            Category category
    ) {}

    public record Survey(

            @Schema(example = "TONGIN", description = "시장 타입 (예: TONGIN, MANGWON)")
            MarketType market,

            @Schema(example = "SOLO", description = "혼잡도 (예: SOLO, COUPLE, GROUP)")
            HumanLevel humanLevel,

            @Schema(example = "MEDIUM", description = "선호 매운맛 정도 (예: NONE, MILD, MEDIUM, HOT)")
            SpicyLevel spicyLevel,

            @Schema(example = "NORMAL", description = "배부름 정도 (예: LIGHT, NORMAL, FULL)")
            FullLevel fullLevel
    ) {}
}
