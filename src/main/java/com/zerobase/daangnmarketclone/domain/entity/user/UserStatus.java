package com.zerobase.daangnmarketclone.domain.entity.user;

import lombok.Getter;

@Getter
public enum UserStatus {

    NORMAL("정상 회원"),
    LEAVE("계정 탈퇴"),
    HUMAN("휴면 상태"),
    FORBIDDEN("계정 정지");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

}
