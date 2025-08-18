package com.mukit.back.domain.market.dto.request;


import com.mukit.back.domain.market.entity.enums.*;

import java.util.List;

public class CourseRequestDTO {

    public record Candidate(
            Long shopId,
            String name,
            List<String> menus,
            Category category
    ) {
    }

    public record Survey(
            MarketType market,
            HumanLevel humanLevel,
            SpicyLevel spicyLevel,
            FullLevel fullLevel
    ) {
    }
}
