package com.mukit.back.domain.market.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mukit.back.domain.market.entity.enums.Category;
import com.mukit.back.domain.market.entity.enums.Holiday;
import com.mukit.back.domain.market.entity.enums.HumanLevel;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

public class ShopRequestDTO {

    @Builder
    public record CreateShop(
            @NotNull Long marketId,
            @NotBlank @Size(max = 120) String name,
            @Size(max = 255) String description,
            List<String> holidays,
            @JsonFormat(pattern = "HH:mm:ss") LocalTime openTime,
            @JsonFormat(pattern = "HH:mm:ss") LocalTime breakStart,
            @JsonFormat(pattern = "HH:mm:ss") LocalTime breakEnd,
            @JsonFormat(pattern = "HH:mm:ss") LocalTime closeTime,
            HumanLevel humanLevel,
            @DecimalMin(value="-180") @DecimalMax(value="180") Double xPos,
            @DecimalMin(value="-90")  @DecimalMax(value="90")  Double yPos,
            Category category,
            @Size(max = 255) String imageUrl,
            @Size(max = 100) String location,
            String phone,
            String note
    ) {
    }

    @Builder
    public record UpdateShop(
            @Size(min = 1, max = 120)
            String name,
            @Size(max = 255)
            String description,

            List<String> holidays,

            LocalTime openTime,
            LocalTime breakStart,
            LocalTime breakEnd,
            LocalTime closeTime,

            HumanLevel humanLevel,

            @DecimalMin(value = "-180") @DecimalMax(value = "180")
            Double xPos,
            @DecimalMin(value = "-90")  @DecimalMax(value = "90")
            Double yPos,

            Category category,

            @Size(max = 255)
            String imageUrl,

            @Size(max = 100)
            String location,
            String phone,

            String note
    ) {
    }
}
