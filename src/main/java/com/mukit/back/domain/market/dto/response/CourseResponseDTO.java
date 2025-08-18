package com.mukit.back.domain.market.dto.response;

import java.util.List;

public class CourseResponseDTO {

    public record ShopDTO(
            Long shopId,
            String name,
            String signatureMenu
    ) {
    }

    public record CourseDTO(
            String title,
            List<ShopDTO> shops
    ) {
    }

    public record CourseResultDTO(
            List<CourseDTO> courses
    ) {
    }
}
