package com.mukit.back.domain.user.service;

import com.mukit.back.domain.user.converter.UserConverter;
import com.mukit.back.domain.user.dto.UserRequestDto;
import com.mukit.back.domain.user.dto.UserResponseDto;
import com.mukit.back.domain.user.entity.User;
import com.mukit.back.domain.user.exception.UserErrorCode;
import com.mukit.back.domain.user.repository.UserRepository;
import com.mukit.back.global.apiPayload.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .marketType(userRequestDto.getMarketType())
                .humanLevel(userRequestDto.getHumanLevel())
                .fullLevel(userRequestDto.getFullLevel())
                .spicyLevel(userRequestDto.getSpicyLevel())
                .build();

        User saved = userRepository.save(user);
        return userConverter.toResponseDto(saved);
    }

    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        return userConverter.toResponseDto(user);
    }
}
