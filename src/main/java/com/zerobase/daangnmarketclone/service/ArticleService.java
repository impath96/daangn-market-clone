package com.zerobase.daangnmarketclone.service;

import com.zerobase.daangnmarketclone.domain.entity.Category;
import com.zerobase.daangnmarketclone.domain.entity.article.Article;
import com.zerobase.daangnmarketclone.domain.entity.article.Content;
import com.zerobase.daangnmarketclone.domain.entity.article.TransactionStatus;
import com.zerobase.daangnmarketclone.domain.entity.user.Region;
import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.entity.user.UserRegion;
import com.zerobase.daangnmarketclone.domain.repository.ArticleRepository;
import com.zerobase.daangnmarketclone.domain.repository.CategoryRepository;
import com.zerobase.daangnmarketclone.domain.repository.RegionRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
import com.zerobase.daangnmarketclone.dto.CreateArticle;
import com.zerobase.daangnmarketclone.dto.UpdateArticle;
import com.zerobase.daangnmarketclone.exception.CustomException;
import com.zerobase.daangnmarketclone.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public void create(String userEmail, CreateArticle createArticle) {

        // 엔티티 조회
        User user = userRepository.findByEmailWithUserRegion(userEmail)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        UserRegion userRegion = user.getRepresentRegion();

        Region region = regionRepository.findById(userRegion.getRegion().getId())
            .orElseThrow(() -> new CustomException(ErrorCode.REGION_NOT_FOUND));

        Category category = categoryRepository.findById(createArticle.getCategoryId())
            .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        // Article 생성
        Article article = Article.builder()
            .status(TransactionStatus.SELL)
            .category(category)
            .region(region)
            .user(user)
            .content(Content.builder()
                .title(createArticle.getTitle())
                .content(createArticle.getContent())
                .price(createArticle.getPrice())
                .build())
            .build();

        // Article 저장
        articleRepository.save(article);

    }

    @Transactional
    public void update(String userEmail, Long articleId, UpdateArticle updateArticle) {

        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!article.isOwner(user)) {
            throw new CustomException(ErrorCode.UN_MATCHED_USER_AND_ARTICLE);
        }

        Content updateContent = Content.builder()
            .content(updateArticle.getContent())
            .title(updateArticle.getTitle())
            .price(updateArticle.getPrice())
            .build();

        article.updateContent(updateContent);

    }
}
