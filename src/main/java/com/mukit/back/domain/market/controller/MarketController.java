package com.mukit.back.domain.market.controller;

import com.mukit.back.domain.market.service.MarketService;
import com.mukit.back.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/market")
@RequiredArgsConstructor
@Tag(name = "Market", description = "마켓")
public class MarketController {
    private final MarketService marketService;

    @Operation(summary = "마켓 정보 추가")
    @PostMapping
    public CustomResponse<?> createMarket() {
        marketService.createMarket();
        return CustomResponse.onSuccess(HttpStatus.SC_OK);
    }
}
