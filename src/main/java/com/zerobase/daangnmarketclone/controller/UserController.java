package com.zerobase.daangnmarketclone.controller;

import com.zerobase.daangnmarketclone.dto.UserDto;
import com.zerobase.daangnmarketclone.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
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

}
