package com.mukit.back.domain.user.service;

import com.mukit.back.domain.user.dto.UserRequestDto;
import com.mukit.back.domain.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(Long userId, UserRequestDto userRequestDto);
}
