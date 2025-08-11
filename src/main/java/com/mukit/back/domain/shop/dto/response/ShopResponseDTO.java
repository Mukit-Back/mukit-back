package com.mukit.back.domain.shop.dto.response;

import com.mukit.back.domain.market.entity.enums.Category;
import com.mukit.back.domain.market.entity.enums.Holiday;
import com.mukit.back.domain.market.entity.enums.HumanLevel;
import lombok.Builder;

import java.time.LocalTime;

public class ShopResponseDTO {

    @Builder
    public record CreateShop(
            Long shopId
    ) {
    }

}
