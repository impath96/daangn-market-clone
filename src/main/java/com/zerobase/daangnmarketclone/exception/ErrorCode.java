package com.zerobase.daangnmarketclone.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    USER_REGION_REGISTRATION_MAX_ERROR(HttpStatus.BAD_REQUEST, "동네는 최대 2개까지만 등록이 가능합니다."),
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 동네를 찾을 수 없습니다."),
    USER_REGION_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 등록된 동네입니다."),
    IMAGE_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "이미지 업로드에 실패했습니다"),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
