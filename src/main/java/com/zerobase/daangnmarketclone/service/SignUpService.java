package com.zerobase.daangnmarketclone.service;

import com.zerobase.daangnmarketclone.domain.entity.user.Profile;
import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.entity.user.UserRole;
import com.zerobase.daangnmarketclone.domain.entity.user.UserStatus;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
import com.zerobase.daangnmarketclone.dto.UserDto;
import com.zerobase.daangnmarketclone.exception.CustomException;
import com.zerobase.daangnmarketclone.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public Long signUp(UserDto userDto) {

        // 1) 이메일 존재 유무 확인
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        // 2) 회원 정보 저장
        User user = userRepository.save(createUser(userDto));

        return user.getId();

    }

    // 회원 생성
    private User createUser(UserDto userDto) {
        return User.builder()
            .email(userDto.getEmail())
            .password(encryptPassword(userDto.getPassword()))
            .profile(new Profile(userDto.getNickname(), userDto.getProfileImage()))
            .phoneNumber(userDto.getPhoneNumber())
            .role(UserRole.ROLE_USER)
            .status(UserStatus.NORMAL)
            .build();
    }

    // 비밀번호 암호화
    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
