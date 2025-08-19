package com.mukit.back.domain.market.dto.response;

import com.mukit.back.domain.market.entity.enums.Category;
import com.mukit.back.domain.market.entity.enums.Holiday;
import com.mukit.back.domain.market.entity.enums.HumanLevel;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

public class ShopResponseDTO {

    @Builder
    public record ShopDetail(
            Long shopId,
            String name,
            String description,
            List<String> holidays,
            LocalTime openTime,
            LocalTime breakStart,
            LocalTime breakEnd,
            LocalTime closeTime,
            HumanLevel humanLevel,
            Double xPos,
            Double yPos,
            Category category,
            String imageUrl,
            String phone,
            String note
    ) {
    }

    @Builder
    public record CreateShop(
            Long shopId
    ) {
    }

}
