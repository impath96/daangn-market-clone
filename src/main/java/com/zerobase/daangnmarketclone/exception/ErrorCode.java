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
    USER_REGION_NOT_FOUND(HttpStatus.NOT_FOUND,"해당하는 유저의 동네를 찾을 수 없습니다."),
    UNMATCHED_USER_REGION(HttpStatus.BAD_REQUEST, "이 동네는 해당 유저가 등록한 동네가 아닙니다."),
    USER_REGION_NOT_REPRESENT(HttpStatus.BAD_REQUEST, "해당 유저의 대표 동네가 아닙니다."),
    CURRENT_USER_POSITION_OUTSIDE(HttpStatus.BAD_REQUEST, "현재 설정된 동네에 위치해 있지 않습니다."),
    UN_MATCHED_USER_INTEREST_CATEGORY(HttpStatus.BAD_REQUEST, "유저가 설정하지 않은 관심 카테고리 입니다."),
    ALREADY_ADDED_INTEREST_CATEGORY(HttpStatus.BAD_REQUEST,"이미 등록된 관심 카테고리 입니다."),
    ALREADY_NOT_ADDED_INTEREST_CATEGORY(HttpStatus.BAD_REQUEST,"이미 등록되어 있지 않은 관심 카테고리 입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 카테고리입니다."),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않은 중고 거래 게시글입니다."),
    UN_MATCHED_USER_AND_ARTICLE(HttpStatus.BAD_REQUEST, "해당 게시글 작성자가 아닙니다."),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
