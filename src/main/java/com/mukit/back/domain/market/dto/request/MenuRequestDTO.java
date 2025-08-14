package com.mukit.back.domain.market.dto.request;

import com.mukit.back.domain.market.entity.enums.FullLevel;
import com.mukit.back.domain.market.entity.enums.SpicyLevel;
import lombok.Builder;

public class MenuRequestDTO {

    @Builder
    public record CreateMenu (
        String name,
        Integer price,
        String description,
        SpicyLevel spicyLevel,
        FullLevel fullLevel
    ) {}

    @Builder
    public record UpdateMenu (
            String name,
            Integer price,
            String description,
            SpicyLevel spicyLevel,
            FullLevel fullLevel
    ) {}
}
