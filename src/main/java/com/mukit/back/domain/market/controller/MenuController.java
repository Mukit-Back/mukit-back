package com.mukit.back.domain.market.controller;

import com.mukit.back.domain.market.dto.request.MenuRequestDTO;
import com.mukit.back.domain.market.dto.response.MenuResponseDTO;
import com.mukit.back.domain.market.service.MenuService;
import com.mukit.back.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Menu", description = "메뉴 API by 준환")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "메뉴 정보 추가")
    @PostMapping(value = "/shops/{shopId}/menus", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 생성 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = """
                            다음과 같은 이유로 실패할 수 있습니다:
                            - 길이가 1에서 100 사이여야 합니다
                            """),
            @ApiResponse(responseCode = "404",
                    description = """
                            다음과 같은 이유로 실패할 수 있습니다:
                            - 메뉴를 찾을 수 없습니다.
                            """),
    })
    public CustomResponse<MenuResponseDTO.CreateMenu> createMenu(
            @PathVariable Long shopId,
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart("request") @Valid MenuRequestDTO.CreateMenu createMenu,
            @RequestPart(value = "menuImage", required = false) MultipartFile menuImage
    ) {
        return CustomResponse.onSuccess(menuService.createMenu(shopId, createMenu, menuImage));
    }

    @Operation(summary = "메뉴 정보 수정")
    @PatchMapping(value = "/menus/{menuId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 생성 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = """
                            다음과 같은 이유로 실패할 수 있습니다:
                            - 길이가 1에서 100 사이여야 합니다
                            """),
            @ApiResponse(responseCode = "404",
                    description = """
                            다음과 같은 이유로 실패할 수 있습니다:
                            - 메뉴를 찾을 수 없습니다.
                            """),
    })
    public CustomResponse<MenuResponseDTO.UpdateMenu> updateMenu(
            @PathVariable Long menuId,
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart("request") @Valid MenuRequestDTO.UpdateMenu updateMenu,
            @RequestPart(value = "menuImage", required = false) MultipartFile menuImage
    ) {
        return CustomResponse.onSuccess(menuService.updateMenu(menuId, updateMenu, menuImage));
    }

    @Operation(summary = "메뉴 정보 가게 기반 리스트 조회")
    @GetMapping("/shops/{shopId}/menus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 생성 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404",
                    description = """
                            다음과 같은 이유로 실패할 수 있습니다:
                            - 가게를 찾을 수 없습니다.
                            """),
    })
    public CustomResponse<MenuResponseDTO.MenuList> getMenus(
            @PathVariable Long shopId
    ) {
        MenuResponseDTO.MenuList menuList = menuService.getMenus(shopId);
        return CustomResponse.onSuccess(menuList);
    }
}
