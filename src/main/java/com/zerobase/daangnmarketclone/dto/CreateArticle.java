package com.zerobase.daangnmarketclone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateArticle {

    private String title;
    private int price;
    private String content;

    private Long categoryId;

    @Builder
    public CreateArticle(String title, int price, String content, Long categoryId) {
        this.title = title;
        this.price = price;
        this.content = content;
        this.categoryId = categoryId;
    }
}
