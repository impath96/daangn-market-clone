package com.zerobase.daangnmarketclone.controller;

import com.zerobase.daangnmarketclone.api.KakaoMapClient;
import com.zerobase.daangnmarketclone.dto.RegionDto;
import com.zerobase.daangnmarketclone.dto.UserDto;
import com.zerobase.daangnmarketclone.dto.UserPositionRequest;
import com.zerobase.daangnmarketclone.api.ApiAddressResponse;
import com.zerobase.daangnmarketclone.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/")
public class UserController {

    private final UserService userService;
    private final KakaoMapClient kakaoMapClient;

    // 내 동네 설정(등록)
    @PostMapping("/locations/{id}")
    public void saveRegion(
        @AuthenticationPrincipal UserDetails loginUser,
        @PathVariable("id") Long regionId
    ) {
        userService.saveRegion(loginUser.getUsername(), regionId);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestParam("nickname") String nickname,
        @RequestPart("profileImage") MultipartFile multipartFile) {

        UserDto userDto = UserDto.builder()
            .nickname(nickname)
            .email(userDetails.getUsername())
            .build();

        return ResponseEntity.ok(userService.updateProfile(userDto, multipartFile));

    }

    /**
     * 내 동네 인증
     *
     * @param userDetails         security 에 저장된 유저 정보
     * @param userPositionRequest 현재 유저 위치 정보
     * @param userRegionId        유저 동네 ID
     */
    @PostMapping("/locations/{userRegionId}/auth")
    public void authenticateRegion(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestBody UserPositionRequest userPositionRequest,
        @PathVariable Long userRegionId

    ) {

        // 위도 경도 -> 지번 주소
        ApiAddressResponse addressDto = kakaoMapClient.getAddressByPosition(
            userPositionRequest.getLatitude(),
            userPositionRequest.getLongitude());

        userService.authenticateUserRegion(
            userDetails.getUsername(),
            convertAddressResponseToRegionDto(addressDto),
            userRegionId);

    }

    private RegionDto convertAddressResponseToRegionDto(ApiAddressResponse addressDto) {
        return RegionDto.builder()
            .state(addressDto.getRegion1DepthName())
            .city(addressDto.getRegion2DepthName())
            .town(addressDto.getRegion3DepthName())
            .build();
    }

}
