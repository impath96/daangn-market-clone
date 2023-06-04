package com.zerobase.daangnmarketclone.domain.entity.article;

import com.zerobase.daangnmarketclone.domain.entity.Category;
import com.zerobase.daangnmarketclone.domain.entity.user.Region;
import com.zerobase.daangnmarketclone.domain.entity.user.User;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Embedded
    private Content content;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Builder
    public Article(Long id, User user, Region region, Category category, Content content,
        TransactionStatus status) {
        this.id = id;
        this.user = user;
        this.region = region;
        this.category = category;
        this.content = content;
        this.status = status;
    }


    public void updateContent(Content content) {
        this.content.update(content);
    }

    public boolean isOwner(User user) {
        return this.user.getId().equals(user.getId());
    }
}
