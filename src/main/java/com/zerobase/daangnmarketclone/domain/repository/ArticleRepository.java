package com.zerobase.daangnmarketclone.domain.repository;

import com.zerobase.daangnmarketclone.domain.entity.article.Article;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends CommonRepository<Article, Long> {

    @Query("SELECT a FROM Article a " +
            "JOIN FETCH a.region " +
            "JOIN FETCH a.user " +
            "JOIN FETCH a.category " +
            "WHERE a.id = :articleId")
    Optional<Article> findByIdWithRegionAndUserAndCategory(Long articleId);

}
