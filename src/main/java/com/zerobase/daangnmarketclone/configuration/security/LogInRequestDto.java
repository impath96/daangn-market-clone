package com.zerobase.daangnmarketclone.configuration.security;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequestDto {

    @Email
    private String email;

    private String password;

}
