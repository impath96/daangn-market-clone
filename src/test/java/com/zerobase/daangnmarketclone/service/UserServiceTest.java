package com.zerobase.daangnmarketclone.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.zerobase.daangnmarketclone.domain.entity.user.Region;
import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.entity.user.UserRegion;
import com.zerobase.daangnmarketclone.domain.repository.RegionRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRegionRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    UserRegionRepository userRegionRepository;

    @BeforeEach
    void init() {
        userRepository.save(createUser("test1@naver.com", "password", "닉네임"));
        regionRepository.save(createRegion("부산시", "사하구", "감천동"));
    }

    User createUser(String email, String password, String nickname) {
        return User.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .build();
    }

    Region createRegion(String state, String city, String town) {
        return Region.builder()
            .state(state)
            .city(city)
            .town(town)
            .build();
    }

    @Transactional
    @Test
    void 유저_동네_등록() {

        String email = "test1@naver.com";
        Long regionId = 1L;

        userService.saveRegion("test1@naver.com", 1L);

        User user = userRepository.findByEmail(email).get();

        UserRegion userRegion = user.getUserRegions().get(0);

        assertThat(userRegion.getUser().getId()).isEqualTo(user.getId());
        assertThat(userRegion.getRegion().getId()).isEqualTo(regionId);

    }

}