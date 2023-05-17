package com.zerobase.daangnmarketclone.configuration.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequestDto {

    private String email;

    private String password;

}
