package com.mukit.back.domain.user.converter;

import com.mukit.back.domain.user.dto.UserResponseDto;
import com.mukit.back.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .userId(user.getId())
                .marketType(user.getMarketType())
                .humanLevel(user.getHumanLevel())
                .fullLevel(user.getFullLevel())
                .spicyLevel(user.getSpicyLevel())
                .build();
    }
}
