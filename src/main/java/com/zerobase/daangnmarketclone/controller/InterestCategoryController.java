package com.zerobase.daangnmarketclone.controller;

import com.zerobase.daangnmarketclone.service.InterestCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InterestCategoryController {

    private final InterestCategoryService interestCategoryService;

    // 관심 카테고리 추가
    @PostMapping("/users/categories/{interestCategoryId}/add")
    public void add(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable Long interestCategoryId
    ) {

        interestCategoryService.add(userDetails.getUsername(), interestCategoryId);

    }

    // 관심 카테고리 삭제
    @PostMapping("/users/categories/{interestCategoryId}/delete")
    public void delete(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable Long interestCategoryId
    ) {

        interestCategoryService.delete(userDetails.getUsername(), interestCategoryId);

    }
}
