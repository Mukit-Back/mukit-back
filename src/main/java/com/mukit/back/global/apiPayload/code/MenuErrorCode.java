package com.mukit.back.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum MenuErrorCode implements BaseErrorCode {

    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "SHOP404", "가게를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
