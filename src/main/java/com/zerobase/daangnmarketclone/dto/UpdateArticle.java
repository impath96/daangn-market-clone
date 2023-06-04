package com.zerobase.daangnmarketclone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateArticle {

    private String title;
    private String content;
    private int price;

    @Builder
    public UpdateArticle(String title, String content, int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }
}
