package com.zerobase.daangnmarketclone.service;

import com.zerobase.daangnmarketclone.controller.ImageUploader;
import com.zerobase.daangnmarketclone.domain.entity.user.Profile;
import com.zerobase.daangnmarketclone.domain.entity.user.Region;
import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.entity.user.UserRegion;
import com.zerobase.daangnmarketclone.domain.repository.RegionRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRegionRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
import com.zerobase.daangnmarketclone.dto.RegionDto;
import com.zerobase.daangnmarketclone.dto.UserDto;
import com.zerobase.daangnmarketclone.exception.CustomException;
import com.zerobase.daangnmarketclone.exception.ErrorCode;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final UserRegionRepository userRegionRepository;
    private final ImageUploader imageUploader;

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

    @Transactional
    public String updateProfile(UserDto userDto, MultipartFile multipartFile) {

        User user = userRepository.findByEmail(userDto.getEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 1) S3에 이미지 업로드
        String uploadUrl = null;

        if (!multipartFile.isEmpty()) {
            uploadUrl = imageUploader.upload(multipartFile);
        }

        // 2) user 정보 수정(이때, S3 url로 설정)
        user.updateProfile(new Profile(userDto.getNickname(), uploadUrl));

        return uploadUrl;
    }

    // 동네 인증
    @Transactional
    public void authenticateUserRegion(String email, RegionDto regionDto,
        Long userRegionId) {

        User user = findUserByEmail(email);
        UserRegion userRegion = findUserRegionById(userRegionId);

        // 해당 지역이 유저가 등록한 지역이 아닐 경우
        if (!userRegion.isOwner(user.getId())) {
            throw new CustomException(ErrorCode.UNMATCHED_USER_REGION);
        }

        // 유저의 대표 동네가 아닐 경우
        if (!userRegion.isRepresent()) {
            throw new CustomException(ErrorCode.USER_REGION_NOT_REPRESENT);
        }

        Region region = userRegion.getRegion();

        // 유저가 현재 설정된 동네에 위치해 있지 않을 경우
        // 어떻게 캡슐화 할지 생각해보기
        if (!(Objects.equals(region.getCity(), regionDto.getCity())
            && Objects.equals(region.getTown(), regionDto.getTown()))) {
            throw new CustomException(ErrorCode.CURRENT_USER_POSITION_OUTSIDE);
        }

        userRegion.authenticate();

    }

    private UserRegion findUserRegionById(Long userRegionId) {
        return userRegionRepository.findById(userRegionId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_REGION_NOT_FOUND));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

}
