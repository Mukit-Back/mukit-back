package com.mukit.back.domain.market.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mukit.back.domain.market.entity.enums.Category;
import com.mukit.back.domain.market.entity.enums.HumanLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

public class ShopRequestDTO {

    @Builder
    public record CreateShop(

            @NotNull
            @Schema(example = "1", description = "마켓 고유 ID")
            Long marketId,

            @NotBlank
            @Size(max = 120)
            @Schema(example = "김밥천국 강남점", description = "상호명 (최대 120자)")
            String name,

            @Size(max = 255)
            @Schema(example = "다양한 김밥과 분식을 합리적인 가격에 제공하는 분식집", description = "상점 설명")
            String description,

            @Schema(example = "[\"SUNDAY\"]", description = "휴무일 리스트")
            List<String> holidays,

            @NotNull
            @JsonFormat(pattern = "HH:mm[:ss]")
            @Schema(example = "09:00:00", description = "영업 시작 시간 (HH:mm 또는 HH:mm:ss)")
            LocalTime openTime,

            @JsonFormat(pattern = "HH:mm[:ss]")
            @Schema(example = "15:00:00", description = "브레이크 타임 시작")
            LocalTime breakStart,

            @JsonFormat(pattern = "HH:mm[:ss]")
            @Schema(example = "16:00:00", description = "브레이크 타임 종료")
            LocalTime breakEnd,

            @NotNull
            @JsonFormat(pattern = "HH:mm[:ss]")
            @Schema(example = "21:00:00", description = "영업 종료 시간")
            LocalTime closeTime,

            @NotNull
            @Schema(example = "SOLO", description = "혼잡도 (예: SOLO, COUPLE, GROUP)")
            HumanLevel humanLevel,

            @NotNull
            @DecimalMin(value="-180") @DecimalMax(value="180")
            @Schema(example = "127.02758", description = "경도")
            Double xPos,

            @NotNull
            @DecimalMin(value="-90") @DecimalMax(value="90")
            @Schema(example = "37.49794", description = "위도")
            Double yPos,

            @NotNull
            @Schema(example = "KOREAN", description = "카테고리 (예: KOREAN, CAFE 등)")
            Category category,

            @Size(max = 100)
            @Schema(example = "서울특별시 강남구 테헤란로 123", description = "주소")
            String location,

            @Pattern(regexp = "^[0-9\\-]+$", message = "전화번호는 숫자와 - 만 허용됩니다.")
            @Schema(example = "02-123-4567", description = "전화번호")
            String phone,

            @Schema(example = "주말에는 23시까지 영업", description = "비고/메모")
            String note
    ) {}

    @Builder
    public record UpdateShop(

            @Size(min = 1, max = 120)
            @Schema(example = "김밥천국 강남점", description = "상호명 (1~120자, 미입력 시 변경 없음)")
            String name,

            @Size(max = 255)
            @Schema(example = "분식과 김밥을 저렴하게 제공하는 분식집", description = "상점 설명 (최대 255자)")
            String description,

            @Schema(example = "[\"SUNDAY\"]", description = "휴무일 리스트")
            List<String> holidays,

            @JsonFormat(pattern = "HH:mm[:ss]")
            @Schema(example = "09:00:00", description = "영업 시작 시간 (HH:mm 또는 HH:mm:ss)")
            LocalTime openTime,

            @JsonFormat(pattern = "HH:mm[:ss]")
            @Schema(example = "15:00:00", description = "브레이크 타임 시작")
            LocalTime breakStart,

            @JsonFormat(pattern = "HH:mm[:ss]")
            @Schema(example = "16:00:00", description = "브레이크 타임 종료")
            LocalTime breakEnd,

            @JsonFormat(pattern = "HH:mm[:ss]")
            @Schema(example = "21:00:00", description = "영업 종료 시간")
            LocalTime closeTime,

            @Schema(example = "SOLO", description = "혼잡도 (예: SOLO, COUPLE, GROUP)")
            HumanLevel humanLevel,

            @DecimalMin(value = "-180") @DecimalMax(value = "180")
            @Schema(example = "127.02758", description = "경도")
            Double xPos,

            @DecimalMin(value = "-90") @DecimalMax(value = "90")
            @Schema(example = "37.49794", description = "위도")
            Double yPos,

            @Schema(example = "KOREAN", description = "카테고리 (예: KOREAN, CAFE 등)")
            Category category,

            @Size(max = 100)
            @Schema(example = "서울특별시 강남구 테헤란로 123", description = "주소")
            String location,

            @Pattern(regexp = "^[0-9\\-]+$", message = "전화번호는 숫자와 - 만 허용됩니다.")
            @Schema(example = "02-123-4567", description = "전화번호")
            String phone,

            @Schema(example = "점심 시간대는 대기 시간이 길 수 있음", description = "비고/메모")
            String note
    ) {}
}
