package com.mukit.back.global.apiPayload.exception.handler;

import com.mukit.back.global.apiPayload.CustomResponse;
import com.mukit.back.global.apiPayload.code.BaseErrorCode;
import com.mukit.back.global.apiPayload.code.GeneralErrorCode;
import com.mukit.back.global.apiPayload.exception.CustomException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //애플리케이션에서 발생하는 커스텀 예외를 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponse<Void>> handleCustomException(CustomException ex) {
        //예외가 발생하면 로그 기록
        log.warn("[ CustomException ]: {}", ex.getCode().getMessage());
        //커스텀 예외에 정의된 에러 코드와 메시지를 포함한 응답 제공
        return ResponseEntity.status(ex.getCode().getHttpStatus())
                .body(ex.getCode().getErrorResponse());
    }

    // 그 외의 정의되지 않은 모든 예외 처리
    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomResponse<String>> handleAllException(Exception ex) {
        log.error("[WARNING] Internal Server Error : {} ", ex.getMessage());
        BaseErrorCode errorCode = GeneralErrorCode.INTERNAL_SERVER_ERROR_500;
        CustomResponse<String> errorResponse = CustomResponse.onFailure(
                errorCode.getCode(),
                errorCode.getMessage(),
                null
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomResponse<Map<String, String>>> ConstraintViolationException(ConstraintViolationException ex) {
        log.error("[ ConstraintViolationException ]: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(v ->
                errors.put(v.getPropertyPath().toString(), v.getMessage()));

        BaseErrorCode errorCode = GeneralErrorCode.BAD_REQUEST_400;

        CustomResponse<Map<String, String>> errorResponse = CustomResponse.onFailure(
                errorCode.getCode(),
                errorCode.getMessage(),
                errors
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse<Map<String, String>>> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("[ MethodArgumentNotValidException ]: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(e ->
                errors.put(e.getDefaultMessage(), e.getDefaultMessage())
        );

        BaseErrorCode errorCode = GeneralErrorCode.BAD_REQUEST_400;
        CustomResponse<Map<String, String>> errorResponse = CustomResponse.onFailure(
                errorCode.getCode(),
                errorCode.getMessage(),
                errors
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }
}
