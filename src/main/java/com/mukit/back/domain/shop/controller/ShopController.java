package com.mukit.back.domain.shop.controller;

import com.mukit.back.domain.shop.dto.request.ShopRequestDTO;
import com.mukit.back.domain.shop.dto.response.ShopResponseDTO;
import com.mukit.back.domain.shop.service.ShopService;
import com.mukit.back.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops")
@Tag(name = "Shop", description = "가게 API by 현빈")
public class ShopController {

    private final ShopService shopService;

    @Operation(summary = "가게 정보 추가")
    @PostMapping
    public CustomResponse<ShopResponseDTO.CreateShop> createShop(
            @RequestBody ShopRequestDTO.CreateShop shopRequestDTO) {
        return CustomResponse.onSuccess(shopService.createShop(shopRequestDTO));
    }
}
