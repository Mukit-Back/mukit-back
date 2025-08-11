package com.mukit.back.domain.shop.dto.response;

import com.mukit.back.domain.market.entity.enums.Category;
import com.mukit.back.domain.market.entity.enums.Holiday;
import com.mukit.back.domain.market.entity.enums.HumanLevel;
import lombok.Builder;

import java.time.LocalTime;

public class ShopResponseDTO {

    @Builder
    public record ShopDetail(
            String name,
            String description,
            Holiday holiday,
            LocalTime openTime,
            LocalTime breakStart,
            LocalTime breakEnd,
            LocalTime closeTime,
            HumanLevel humanLevel,
            Double xPos,
            Double yPos,
            Category category,
            String imageUrl
    ) {
    }

    @Builder
    public record CreateShop(
            Long shopId
    ) {
    }

}
