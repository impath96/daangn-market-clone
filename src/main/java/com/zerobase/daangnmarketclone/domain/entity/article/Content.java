package com.zerobase.daangnmarketclone.domain.entity.article;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Content {

    private String title;

    private int price;

    private String content;

    @Builder
    public Content(String title, int price, String content) {
        this.title = title;
        this.price = price;
        this.content = content;
    }
}
