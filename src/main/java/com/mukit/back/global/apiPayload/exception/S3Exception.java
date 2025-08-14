package com.mukit.back.global.apiPayload.exception;

import com.mukit.back.global.apiPayload.code.BaseErrorCode;

public class S3Exception extends CustomException {
    public S3Exception(BaseErrorCode code) {
        super(code);
    }
}
