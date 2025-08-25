package com.mukit.back.domain.user.controller;

import com.mukit.back.domain.user.dto.UserRequestDto;
import com.mukit.back.domain.user.dto.UserResponseDto;
import com.mukit.back.domain.user.service.UserService;
import com.mukit.back.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User", description = "유저")
public class UserController {

    private final UserService userService;

    // 일단은 pk로
    @Operation(summary = "유저 생성", description = "유저를 생성합니다.")
    @PostMapping
    public ResponseEntity<CustomResponse<UserResponseDto>> createUser(
            @RequestBody @Valid UserRequestDto userRequestDto
    ) {
        UserResponseDto response = userService.createUser(userRequestDto);
        return ResponseEntity.ok(CustomResponse.onSuccess(response));
    }

    // 유저 조회
    @Operation(summary = "유저 조회", description = "유저를 조회합니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<CustomResponse<UserResponseDto>> getUser(
            @PathVariable Long userId
    ) {
        UserResponseDto response = userService.getUser(userId);
        return ResponseEntity.ok(CustomResponse.onSuccess(response));
    }
}
