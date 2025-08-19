package com.mukit.back.domain.market.dto.request;

import com.mukit.back.domain.market.entity.enums.FullLevel;
import com.mukit.back.domain.market.entity.enums.SpicyLevel;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

public class MenuRequestDTO {

    @Builder
    public record CreateMenu (
        @Length(min = 1, max = 100)
        String name,
        Integer price,
        @Length(min = 1, max = 2000)
        String description,
        SpicyLevel spicyLevel,
        FullLevel fullLevel,
        String note
    ) {}

    @Builder
    public record UpdateMenu (
            @Length(min = 1, max = 100)
            String name,
            Integer price,
            @Length(min = 1, max = 2000)
            String description,
            SpicyLevel spicyLevel,
            FullLevel fullLevel
    ) {}
}
