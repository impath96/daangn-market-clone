package com.zerobase.daangnmarketclone.dto;

import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String email;     // 이메일

    private String password;  // 비밀번호

    private String nickname;  // 닉네임

    // (선택사항) 휴대폰번호, 프로필 사진
    private String phoneNumber;
    private String profileImage;

    private UserRole role;

    public static User toEntity(UserDto userDto) {
        return User.builder()
            .email(userDto.getEmail())
            .password(userDto.getPassword())
            .nickname(userDto.getNickname())
            .role(userDto.getRole())
            .phoneNumber(userDto.getPhoneNumber())
            .imageUrl(userDto.getProfileImage())
            .build();
    }

}
