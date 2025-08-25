package com.mukit.back.domain.market.controller;

import com.mukit.back.domain.market.dto.request.RouteRequestDTO;
import com.mukit.back.domain.market.dto.response.RouteResponseDTO;
import com.mukit.back.domain.market.service.DirectionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Tag(name = "Direction", description = "경로 탐색 API")
public class DirectionsController {

    private final DirectionsService service;
    private final RouteCache cache;

    @Operation(summary = "추천 경로 탐색")
    @GetMapping("/directions")
    public RouteResponseDTO directions(@Valid @ModelAttribute RouteRequestDTO req) {
        String key = DigestUtils.md5DigestAsHex(
                (req.alat()+","+req.alng()+"->"+req.blat()+","+req.blng()+"|"+req.prioritySafe())
                        .getBytes(StandardCharsets.UTF_8));
        return cache.getOrPut(key, () -> service.getRoute(req));
    }
}