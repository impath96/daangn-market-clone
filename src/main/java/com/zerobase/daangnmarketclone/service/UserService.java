package com.zerobase.daangnmarketclone.service;

import com.zerobase.daangnmarketclone.domain.entity.user.Region;
import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.entity.user.UserRegion;
import com.zerobase.daangnmarketclone.domain.repository.RegionRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRegionRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
import com.zerobase.daangnmarketclone.exception.CustomException;
import com.zerobase.daangnmarketclone.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final UserRegionRepository userRegionRepository;

    // 예외 처리가 로직의 대부분... 어떻게 하지
    @Transactional
    public void saveRegion(String email, Long regionId) {

        // 1) 엔티티 조회(User, Region)
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Region region = regionRepository.findById(regionId)
            .orElseThrow(() -> new CustomException(ErrorCode.REGION_NOT_FOUND));


        int count = userRegionRepository.countByUserId(user.getId());

        // 2) 등록된 동네가 2개 이상이면 - exception 발생
        if (count >= 2) {
            throw new CustomException(ErrorCode.USER_REGION_REGISTRATION_MAX_ERROR);
        }

        // 3) 이미 등록된 동네일 경우 - exception 발생
        if (user.hasRegion(region)) {
            throw new CustomException(ErrorCode.USER_REGION_DUPLICATED);
        }

        // 4) 등록된 동네가 없거나 1개 있을 경우
        //  - 해당 유저의 동네를 region으로 등록(이때 대표 동네로 설정, 인증은 X)
        UserRegion userRegion = UserRegion.create(user, region);

        userRegionRepository.save(userRegion);

    }

}
