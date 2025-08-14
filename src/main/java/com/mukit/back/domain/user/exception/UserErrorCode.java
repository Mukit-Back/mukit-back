package com.mukit.back.domain.user.exception;

import com.mukit.back.global.apiPayload.CustomResponse;
import com.mukit.back.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return this.name(); // enum 이름을 코드로 사용
    }

    public CustomResponse<Void> getErrorResponse() {
        return CustomResponse.onFailure(name(), message);
    }
}
