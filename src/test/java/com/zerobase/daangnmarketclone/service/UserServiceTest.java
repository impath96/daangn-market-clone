package com.zerobase.daangnmarketclone.service;

import static com.zerobase.daangnmarketclone.exception.ErrorCode.REGION_NOT_FOUND;
import static com.zerobase.daangnmarketclone.exception.ErrorCode.USER_NOT_FOUND;
import static com.zerobase.daangnmarketclone.exception.ErrorCode.USER_REGION_DUPLICATED;
import static com.zerobase.daangnmarketclone.exception.ErrorCode.USER_REGION_REGISTRATION_MAX_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import com.zerobase.daangnmarketclone.domain.entity.user.Region;
import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.repository.RegionRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
import com.zerobase.daangnmarketclone.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RegionRepository regionRepository;


    User createUser(String email, String password) {
        return User.builder()
            .email(email)
            .password(password)
            .build();
    }

    Region createRegion(String state, String city, String town) {
        return Region.builder()
            .state(state)
            .city(city)
            .town(town)
            .build();
    }

    @DisplayName("동네 설정 시 유저가 존재하지 않는 이메일로 시도할 경우 예외 발생")
    @Test
    void 동네_설정_시_존재하지_않는_이메일_일경우_예외_발생() {
        // given
        // when
        // then
        assertThatThrownBy(
            () -> userService.addRegion("test@naver.com", 1L)
        ).isInstanceOf(CustomException.class)
            .hasMessage(USER_NOT_FOUND.getMessage());


    }
    @DisplayName("동네 설정 시 동네가 존재하지 않는 경우 예외 발생")
    @Test
    void 동네_설정_시_동네_존재하지_않는_경우_예외_발생() {
        // given
        userRepository.save(createUser("test@naver.com", "password"));

        // when
        // then
        assertThatThrownBy(
                () -> userService.addRegion("test@naver.com", 1L)
            ).isInstanceOf(CustomException.class)
            .hasMessage(REGION_NOT_FOUND.getMessage());
    }

    @DisplayName("동네 설정 시 유저가 이미 2개의 동네를 등록했을 경우 예외 발생")
    @Test
    void 동네_설정_시_이미_2개_동네_등록헀을_경우_예외_발생() {

        User user = createUser("test@naver.com", "password");
        Region region1 = createRegion("부산시", "사하구", "감천동");
        Region region2 = createRegion("부산시", "사하구", "괴정동");
        user.addRegion(region1);
        user.addRegion(region2);
        regionRepository.save(region1);
        regionRepository.save(region2);
        userRepository.save(user);


        Long regionId = region1.getId();

        assertThatThrownBy(
                () -> userService.addRegion("test@naver.com", regionId)
            ).isInstanceOf(CustomException.class)
            .hasMessage(USER_REGION_REGISTRATION_MAX_ERROR.getMessage());

    }

    @DisplayName("동네 설정 시 이미 등록되어 있는 동네를 등록할 경우 예외 발생")
    @Test
    void 동네_설정_시_이미_등록되어_있는_동네_일_경우_예외_발생() {

        // given
        User user = createUser("test@naver.com", "password");
        Region region1 = createRegion("부산시", "사하구", "감천동");
        Region region2 = createRegion("부산시", "사하구", "괴정동");
        user.addRegion(region1);
        regionRepository.save(region1);
        regionRepository.save(region2);
        userRepository.save(user);
        Long alreadyExistsRegionId = region1.getId();

        // when
        // then
        assertThatThrownBy(
            () -> userService.addRegion("test@naver.com", alreadyExistsRegionId)
        ).isInstanceOf(CustomException.class)
            .hasMessage(USER_REGION_DUPLICATED.getMessage());
    }

    @DisplayName("동네 설정을 성공적으로 완료하면 해당 동네가 대표 동네가 된다.")
    @Test
    void 동네_설정_성공적으로_완료_시_유저_동네_목록_업데이트() {
        // given
        User user = createUser("test@naver.com", "password");
        Region region1 = createRegion("부산시", "사하구", "감천동");
        Region region2 = createRegion("부산시", "사하구", "괴정동");
        // user.addRegion() 말고 userRegionRepository.save(UserRegion.create(user, region)
        // 으로 하려면 계속 저장이 안되는 문제가 있음 -> 어떻게 해결하지?
        user.addRegion(region1);
        regionRepository.save(region1);
        regionRepository.save(region2);
        userRepository.save(user);

        Long regionId = region2.getId();

        // when
        userService.addRegion("test@naver.com", regionId);

        // then
        User findUser = userRepository.findByEmailWithUserRegion("test@naver.com").orElse(null);

        assertThat(findUser).isNotNull();
        assertThat(findUser.getUserRegions()).hasSize(2)
                .extracting("region", "isRepresent")
                    .containsExactlyInAnyOrder(
                        tuple(region1, false),
                        tuple(region2, true)
                    );
        assertThat(findUser.getRepresentRegion().isRepresent()).isTrue();

    }
}