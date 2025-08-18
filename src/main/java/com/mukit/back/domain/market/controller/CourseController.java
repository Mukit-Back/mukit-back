package com.mukit.back.domain.market.controller;

import com.mukit.back.domain.market.dto.request.CourseRequestDTO;
import com.mukit.back.domain.market.dto.response.CourseResponseDTO;
import com.mukit.back.domain.market.service.CourseService;
import com.mukit.back.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
@Tag(name = "Course", description = "AI 코스 추천 API by 현빈")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "AI 코스 추천", description = """
            market(시장) - TONGIN, MANGWON, NAMDAEMUN\n
            humanLevel(동행) - SOLO, COUPLE, FAMILY, GROUP\n
            spicyLevel(맵기) - NONE, MILD, MEDIUM, HOT, EXTREME\n
            fullLevel(배부름) - LIGHT, NORMAL, FULL            
            """)
    @PostMapping
    public CustomResponse<CourseResponseDTO.CourseResultDTO> createCourse(
            @RequestBody CourseRequestDTO.Survey survey
    ) {
        return CustomResponse.onSuccess(courseService.recommendCourse(survey));
    }
}
