package com.mukit.back.domain.market.controller;

import com.mukit.back.domain.market.dto.request.MenuRequestDTO;
import com.mukit.back.domain.market.dto.response.MenuResponseDTO;
import com.mukit.back.domain.market.service.MenuService;
import com.mukit.back.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping(value = "/shops/{shopId}/menus", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public CustomResponse<MenuResponseDTO.CreateMenu> createMenu(
            @PathVariable Long shopId,
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart("request") MenuRequestDTO.CreateMenu createMenu,
            @RequestPart("menuImage") MultipartFile menuImage
    ) {
        return CustomResponse.onSuccess(menuService.createMenu(shopId, createMenu, menuImage));
    }

    @PatchMapping(value = "/menus/{menuId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public CustomResponse<MenuResponseDTO.UpdateMenu> updateMenu(
            @PathVariable Long menuId,
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart("request") MenuRequestDTO.UpdateMenu updateMenu,
            @RequestPart("menuImage") MultipartFile menuImage
    ) {
        return CustomResponse.onSuccess(menuService.updateMenu(menuId, updateMenu, menuImage));
    }

    @DeleteMapping("/menus/{menuId}")
    public CustomResponse<String> deleteMenu(
            @PathVariable Long menuId
    ) {
        menuService.deleteMenu(menuId);
        return CustomResponse.onSuccess("삭제 성공");
    }

    @GetMapping("/shops/{shopId}/menus")
    public CustomResponse<MenuResponseDTO.MenuList> getMenus(
            @PathVariable Long shopId
    ) {
        MenuResponseDTO.MenuList menuList = menuService.getMenus(shopId);
        return CustomResponse.onSuccess(menuList);
    }
}
