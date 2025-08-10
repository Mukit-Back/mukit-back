package com.mukit.back.domain.user.dto;

import com.mukit.back.domain.user.entity.Market;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRequestDto {
    private Long userId;
    private Market market;
    private Integer humanLevel;
    private Integer spicyLevel;
    private Integer fullLevel;
}
