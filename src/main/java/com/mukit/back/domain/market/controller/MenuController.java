package com.mukit.back.domain.market.controller;

import com.mukit.back.domain.market.dto.request.MenuRequestDTO;
import com.mukit.back.domain.market.dto.response.MenuResponseDTO;
import com.mukit.back.domain.market.service.MenuService;
import com.mukit.back.global.apiPayload.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/shops/{shopId}/menus")
    public CustomResponse<MenuResponseDTO.CreateMenu> createMenu(
            @PathVariable Long shopId,
            @RequestBody MenuRequestDTO.CreateMenu createMenu
    ) {
        return CustomResponse.onSuccess(menuService.createMenu(shopId, createMenu));
    }

    @PatchMapping("/menus/{menuId}")
    public CustomResponse<MenuResponseDTO.UpdateMenu> updateMenu(
            @PathVariable Long menuId,
            @RequestBody MenuRequestDTO.UpdateMenu updateMenu
    ) {
        return CustomResponse.onSuccess(menuService.updateMenu(menuId, updateMenu));
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
