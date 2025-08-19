package com.mukit.back.domain.market.dto.response;

import com.mukit.back.domain.market.entity.enums.FullLevel;
import com.mukit.back.domain.market.entity.enums.SpicyLevel;
import lombok.Builder;

import java.util.List;

public class MenuResponseDTO {

    @Builder
    public record CreateMenu (
            Long menuId
    ) {}

    @Builder
    public record UpdateMenu (
            Long menuId,
            String name,
            Integer price,
            String description,
            SpicyLevel spicyLevel,
            FullLevel fullLevel
    ) {}

    @Builder
    public record MenuList (
            List<MenuPreview> menuPreviewList
    ) {}

    @Builder
    public record MenuPreview (
            Long menuId,
            String name,
            Integer price,
            String description,
            String ImageUrl,
            String note
    ) {}
}
