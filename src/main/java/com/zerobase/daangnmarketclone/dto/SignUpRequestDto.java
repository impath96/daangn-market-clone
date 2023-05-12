package com.zerobase.daangnmarketclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {


    private String email;     // 이메일

    private String password;  // 비밀번호

    private String nickname;  // 닉네임

    // (선택사항) 휴대폰번호, 프로필 사진
    private String phoneNumber;
    private String profileImage;

    public UserDto toUserDto() {

        return UserDto.builder()
            .email(this.email)
            .password(this.password)
            .nickname(this.nickname)
            .phoneNumber(this.phoneNumber)
            .profileImage(this.profileImage)
            .build();

    }
}
