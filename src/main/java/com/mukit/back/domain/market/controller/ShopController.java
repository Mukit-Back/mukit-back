package com.mukit.back.domain.market.controller;

import com.mukit.back.domain.market.dto.request.ShopRequestDTO;
import com.mukit.back.domain.market.dto.response.ShopResponseDTO;
import com.mukit.back.domain.market.service.ShopService;
import com.mukit.back.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops")
@Tag(name = "Shop", description = "가게 API by 현빈")
@Validated
public class ShopController {

    private final ShopService shopService;

    @Operation(summary = "가게 정보 추가")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public CustomResponse<ShopResponseDTO.CreateShop> createShop(
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart("request") @Valid ShopRequestDTO.CreateShop createShopDTO,
            @RequestPart(value = "shopImage", required = false) MultipartFile shopImage) {
        return CustomResponse.onSuccess(shopService.createShop(createShopDTO, shopImage));
    }

    @Operation(summary = "가게 정보 전부 삭제")
    @DeleteMapping
    public CustomResponse<String> deleteAllShop() {
        shopService.deleteAllShop();
        return CustomResponse.onSuccess("가게 정보 전부 삭제 완료");
    }


    @Operation(summary = "가게 정보 수정")
    @PatchMapping("/{shopId}")
    public CustomResponse<String> updateShop(@PathVariable Long shopId, @RequestBody @Valid ShopRequestDTO.UpdateShop shopRequestDTO) {
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

    @Operation(summary = "가게 정보 조회 (가게 이름 기반)")
    @GetMapping("/shop-name")
    public CustomResponse<ShopResponseDTO.ShopDetail> getShop(@RequestParam String shopName) {
        return CustomResponse.onSuccess(shopService.getShopDetailByName(shopName));
    }

    @Operation(summary = "가게 목록 조회")
    @GetMapping
    public CustomResponse<List<ShopResponseDTO.ShopDetail>> getShops() {
        return CustomResponse.onSuccess(shopService.getShopList());
    }


}
