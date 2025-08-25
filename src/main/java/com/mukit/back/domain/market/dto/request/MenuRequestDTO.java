package com.mukit.back.domain.market.dto.request;

import com.mukit.back.domain.market.entity.enums.FullLevel;
import com.mukit.back.domain.market.entity.enums.SpicyLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

public class MenuRequestDTO {

    @Builder
    public record CreateMenu (

            @Length(min = 1, max = 100)
            @Schema(example = "김치찌개", description = "메뉴 이름 (1~100자)")
            String name,

            @Min(1) @Max(200000)
            @Schema(example = "8000", description = "가격 (1원 ~ 200,000원)")
            Integer price,

            @Length(min = 1, max = 2000)
            @Schema(example = "매콤한 김치찌개, 공기밥 포함", description = "메뉴 설명 (1~2000자)")
            String description,

            @Schema(example = "MEDIUM", description = "매운맛 정도 (예: NONE, MILD, MEDIUM, HOT)")
            SpicyLevel spicyLevel,

            @Schema(example = "NORMAL", description = "배부름 정도 (예: LIGHT, NORMAL, FULL)")
            FullLevel fullLevel,

            @Schema(example = "점심 특선 메뉴", description = "비고/메모")
            String note
    ) {}

    @Builder
    public record UpdateMenu (

            @Length(min = 1, max = 100)
            @Schema(example = "된장찌개", description = "수정할 메뉴 이름 (1~100자)")
            String name,

            @Schema(example = "9000", description = "수정할 가격 (1원 ~ 200,000원)")
            Integer price,

            @Length(min = 1, max = 2000)
            @Schema(example = "구수한 된장찌개, 공기밥 포함", description = "수정할 설명 (1~2000자)")
            String description,

            @Schema(example = "MILD", description = "수정할 매운맛 정도 (예: NONE, MILD, MEDIUM, HOT)")
            SpicyLevel spicyLevel,

            @Schema(example = "LIGHT", description = "수정할 배부름 정도 (예: LIGHT, NORMAL, FULL)")
            FullLevel fullLevel
    ) {}
}
