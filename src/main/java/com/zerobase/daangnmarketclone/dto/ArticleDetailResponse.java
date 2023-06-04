package com.zerobase.daangnmarketclone.dto;

import com.zerobase.daangnmarketclone.domain.entity.article.Article;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDetailResponse {
/*
*   게시글 ID, 작성자 프로필 정보(프로필 사진, 닉네임), 제목, 가격, 본문, 카테고리, 게시 시간, 수정 시간, 거래 상태
*/
    private Long articleId;
    private UserProfileResponse userResponse;
    private String reginName;
    private String categoryName;
    private String title;
    private String content;
    private Integer price;
    private String statue;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    @Builder
    public ArticleDetailResponse(Long articleId, UserProfileResponse userResponse, String reginName,
        String categoryName, String title, String content, Integer price, String statue,
        LocalDateTime createdDatetime, LocalDateTime updatedDatetime) {
        this.articleId = articleId;
        this.userResponse = userResponse;
        this.reginName = reginName;
        this.categoryName = categoryName;
        this.title = title;
        this.content = content;
        this.price = price;
        this.statue = statue;
        this.createdDatetime = createdDatetime;
        this.updatedDatetime = updatedDatetime;
    }

    public static ArticleDetailResponse of(final Article article) {
        return ArticleDetailResponse.builder()
            .articleId(article.getId())
            .userResponse(UserProfileResponse.builder()
                .nickname(article.getUser().getProfile().getNickname())
                .imageUrl(article.getUser().getProfile().getImageUrl())
                .build())
            .reginName(article.getRegion().getTown())
            .categoryName(article.getCategory().getName())
            .title(article.getContent().getTitle())
            .content(article.getContent().getContent())
            .price(article.getContent().getPrice())
            .statue(article.getStatus().name())
            .createdDatetime(article.getCreatedDateTime())
            .updatedDatetime(article.getUpdatedDateTime())
            .build();
    }

}
