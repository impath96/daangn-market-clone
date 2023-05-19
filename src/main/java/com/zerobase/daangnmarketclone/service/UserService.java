package com.zerobase.daangnmarketclone.service;

import com.zerobase.daangnmarketclone.controller.ImageUploader;
import com.zerobase.daangnmarketclone.domain.entity.user.Profile;
import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
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
    private final ImageUploader imageUploader;

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
