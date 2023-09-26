package com.zerobase.daangnmarketclone.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zerobase.daangnmarketclone.domain.entity.Category;
import com.zerobase.daangnmarketclone.domain.entity.InterestCategory;
import com.zerobase.daangnmarketclone.domain.entity.user.Profile;
import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.entity.user.UserRole;
import com.zerobase.daangnmarketclone.domain.entity.user.UserStatus;
import com.zerobase.daangnmarketclone.domain.repository.CategoryRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
import com.zerobase.daangnmarketclone.dto.UserDto;
import com.zerobase.daangnmarketclone.exception.CustomException;
import com.zerobase.daangnmarketclone.exception.ErrorCode;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class SignUpServiceTest {

    @Autowired
    SignUpService signUpService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CategoryRepository categoryRepository;


    private User createUser(String email, String password, String nickname) {
        return User.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .profile(new Profile(nickname, "imageURL"))
            .role(UserRole.ROLE_USER)
            .status(UserStatus.NORMAL)
            .build();
    }

    @DisplayName("회원가입 시 기본 권한은 USER 이다.")
    @Test
    void userIsAssignedDefaultRoleWhenSigningUp() {
        // given
        UserDto dto = new UserDto();
        dto.setEmail("test@naver.com");
        dto.setPassword("password");
        dto.setNickname("닉네임");

        // when
        Long userId = signUpService.signUp(dto);

        User savedUser = userRepository.findById(userId).orElse(null);

        assertThat(userId).isNotNull();
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.hasRole(UserRole.ROLE_USER)).isTrue();

    }

    @DisplayName("회원가입 시 기본적으로 모든 관심 카테고리가 등록된 상태여야 한다.")
    @Test
    void test3() {

        // given
        UserDto dto = new UserDto();
        dto.setEmail("test@naver.com");
        dto.setPassword("password");
        dto.setNickname("닉네임");

        categoryRepository.saveAll(createCategories());


        // when
        Long userId = signUpService.signUp(dto);
        User user = userRepository.findById(userId).orElse(null);

        assertThat(user).isNotNull();

        List<InterestCategory> userInterestCategories = user.getInterestCategories();

        assertThat(userInterestCategories).hasSize(14);

    }

    @DisplayName("이메일이 중복될 경우 예외가 발생한다.")
    @Test
    void throwExceptionWhenEmailIsDuplicated() {
        // given
        userRepository.save(createUser("test@naver.com", "password", "닉네임"));

        UserDto dto = new UserDto();
        dto.setEmail("test@naver.com");
        dto.setPassword("password");
        dto.setNickname("닉네임");


        // when, then
        CustomException customException = assertThrows(CustomException.class,
            () -> signUpService.signUp(dto));

        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.DUPLICATED_EMAIL);

    }

    @DisplayName("비밀번호는 암호화를 통해 저장되어야 한다.")
    @Test
    void passwordEncoding() {
        // given
        UserDto dto = new UserDto();
        dto.setEmail("test0@naver.com");
        dto.setPassword("password");
        dto.setNickname("닉네임");

        // when
        Long userId = signUpService.signUp(dto);
        User user = userRepository.findById(userId).orElse(null);

        // then
        assertThat(user).isNotNull();
        assertThat(passwordEncoder.matches("password", user.getPassword())).isTrue();

    }




    private List<Category> createCategories() {
        return List.of(
            new Category(1L, "디지털기기"),
            new Category(2L, "생활가전"),
            new Category(3L, "가구/인테리어"),
            new Category(4L, "생활/주방"),
            new Category(5L, "여성의류"),
            new Category(6L, "뷰티/미용"),
            new Category(7L, "남성패션/잡화"),
            new Category(8L, "스포츠/레저"),
            new Category(9L, "취미/게임/음반"),
            new Category(10L, "도서"),
            new Category(11L, "티켓/교환권"),
            new Category(12L, "가공식품"),
            new Category(13L, "반료동물용품"),
            new Category(14L, "식물")
        );
    }
}