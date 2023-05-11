package com.zerobase.daangnmarketclone.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.entity.user.UserRole;
import com.zerobase.daangnmarketclone.domain.entity.user.UserStatus;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
import com.zerobase.daangnmarketclone.dto.SignUpRequestDto;
import com.zerobase.daangnmarketclone.exception.CustomException;
import com.zerobase.daangnmarketclone.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SignUpServiceTest {

    @Autowired
    SignUpService signUpService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        userRepository.save(createUser("test01@naver.com", "password", "닉네임"));
    }

    private User createUser(String email, String password, String nickname) {
        return User.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .nickname(nickname)
            .role(UserRole.ROLE_USER)
            .status(UserStatus.NORMAL)
            .build();
    }

    @DisplayName("회원가입 성공")
    @Test
    void 회원가입() {
        // given
        SignUpRequestDto dto = new SignUpRequestDto();
        dto.setEmail("test02@naver.com");
        dto.setPassword("password");
        dto.setNickname("닉네임");

        // when
        Long userId = signUpService.signUp(dto);
        User user = userRepository.findById(userId).get();

        // then
        assertThat(user.getEmail()).isEqualTo(dto.getEmail());
        assertThat(passwordEncoder.matches(dto.getPassword(), user.getPassword())).isTrue();
        assertThat(user.getNickname()).isEqualTo(dto.getNickname());
        assertThat(user.getPhoneNumber()).isNull();
        assertThat(user.getImageUrl()).isNull();
        assertThat(user.getRole()).isEqualTo(UserRole.ROLE_USER);
        assertThat(user.getUserStatus()).isEqualTo(UserStatus.NORMAL);
    }

    @DisplayName("중복 이메일 존재 - 회원가입 실패")
    @Test
    void 중복_이메일_예외() {
        // given
        SignUpRequestDto dto = new SignUpRequestDto();
        dto.setEmail("test01@naver.com");
        dto.setPassword("password");
        dto.setNickname("닉네임");

        // when, then
        CustomException customException = assertThrows(CustomException.class,
            () -> signUpService.signUp(dto));

        assertThat(ErrorCode.DUPLICATED_EMAIL).isEqualTo(customException.getErrorCode());

    }
}