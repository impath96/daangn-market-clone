package com.zerobase.daangnmarketclone.controller;

import com.zerobase.daangnmarketclone.dto.CreateArticle;
import com.zerobase.daangnmarketclone.dto.UpdateArticle;
import com.zerobase.daangnmarketclone.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    // 게시글 수정
    // 클라이언트에서 변경된 값과 변경되지 않은 값들은 기존의 값들을 전달해준다고 가정
    @PutMapping("/articles/{article-id}")
    public void update(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable("article-id") Long articleId,
        @RequestBody UpdateArticle request
    ) {
        articleService.update(userDetails.getUsername(), articleId, request);
    }

}
