package com.zerobase.daangnmarketclone.domain.entity.user;

import lombok.Getter;

@Getter
public enum UserRole {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

}
