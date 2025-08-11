package com.mukit.back.domain.shop.dto.request;

import com.mukit.back.domain.market.entity.enums.Category;
import com.mukit.back.domain.market.entity.enums.Holiday;
import com.mukit.back.domain.market.entity.enums.HumanLevel;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalTime;

public class ShopRequestDTO {

    @Builder
    public record CreateShop(
            @NotNull Long marketId,
            @NotBlank @Size(max = 120) String name,
            @Size(max = 255) String description,
            Holiday holiday,
            LocalTime openTime,
            LocalTime breakStart,
            LocalTime breakEnd,
            LocalTime closeTime,
            HumanLevel humanLevel,
            @DecimalMin(value="-180") @DecimalMax(value="180") Double xPos,
            @DecimalMin(value="-90")  @DecimalMax(value="90")  Double yPos,
            Category category,
            @Size(max = 255) String imageUrl,
            @Size(max = 100) String location
    ) {
    }
}
