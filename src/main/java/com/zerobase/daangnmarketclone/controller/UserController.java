package com.zerobase.daangnmarketclone.controller;

import com.zerobase.daangnmarketclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
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

}
