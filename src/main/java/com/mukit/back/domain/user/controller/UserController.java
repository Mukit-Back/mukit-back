package com.mukit.back.domain.user.controller;

import com.mukit.back.domain.user.dto.UserRequestDto;
import com.mukit.back.domain.user.dto.UserResponseDto;
import com.mukit.back.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 일단은 pk로
    @Operation(summary = "유저 생성", description = "유저를 생성합니다.")
    @PostMapping("/{userId}")
    public ResponseEntity<UserResponseDto> createUser(
            @PathVariable Long userId,
            @RequestBody UserRequestDto userRequestDto
    ) {
        UserResponseDto response = userService.createUser(userRequestDto);
        return ResponseEntity.ok(response);
    }
}
