package com.mukit.back.domain.market.controller;

import com.mukit.back.domain.market.dto.request.ShopRequestDTO;
import com.mukit.back.domain.market.dto.response.ShopResponseDTO;
import com.mukit.back.domain.market.service.ShopService;
import com.mukit.back.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "가게 정보 수정")
    @PatchMapping("/{shopId}")
    public CustomResponse<String> updateShop(@PathVariable Long shopId, @RequestBody ShopRequestDTO.UpdateShop shopRequestDTO) {
        shopService.updateShop(shopId, shopRequestDTO);
        return CustomResponse.onSuccess("가게 정보 수정 완료");
    }

    @Operation(summary = "가게 정보 삭제")
    @DeleteMapping("/{shopId}")
    public CustomResponse<String> deleteShop(@PathVariable Long shopId) {
        shopService.deleteShop(shopId);
        return CustomResponse.onSuccess("가게 정보 삭제 완료");
    }

    @Operation(summary = "가게 정보 조회")
    @GetMapping("/{shopId}")
    public CustomResponse<ShopResponseDTO.ShopDetail> getShop(@PathVariable Long shopId) {
        return CustomResponse.onSuccess(shopService.getShopDetail(shopId));
    }

    @Operation(summary = "가게 목록 조회")
    @GetMapping
    public CustomResponse<List<ShopResponseDTO.ShopDetail>> getShops() {
        return CustomResponse.onSuccess(shopService.getShopList());
    }


}
