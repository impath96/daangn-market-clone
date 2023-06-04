package com.zerobase.daangnmarketclone.controller;

import com.zerobase.daangnmarketclone.dto.CreateArticle;
import com.zerobase.daangnmarketclone.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    // 중고 거래 게시글 작성
    @PostMapping("/articles")
    public void write(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestBody CreateArticle request
    ) {
        articleService.create(userDetails.getUsername(), request);
    }
}
