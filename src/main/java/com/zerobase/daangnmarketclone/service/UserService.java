package com.zerobase.daangnmarketclone.service;

import com.zerobase.daangnmarketclone.domain.entity.user.Region;
import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.entity.user.UserRegion;
import com.zerobase.daangnmarketclone.domain.repository.RegionRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRegionRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
import com.zerobase.daangnmarketclone.controller.ImageUploader;
import com.zerobase.daangnmarketclone.domain.entity.user.Profile;
import com.zerobase.daangnmarketclone.dto.UserDto;
import com.zerobase.daangnmarketclone.exception.CustomException;
import com.zerobase.daangnmarketclone.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
}
