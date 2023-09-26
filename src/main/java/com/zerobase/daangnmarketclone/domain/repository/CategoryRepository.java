package com.zerobase.daangnmarketclone.domain.repository;

import com.zerobase.daangnmarketclone.domain.entity.Category;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends CommonRepository<Category, Long> {

    @Transactional(readOnly = true)
    List<Category> findAll();

    List<Category> saveAll(Iterable<Category> categories);
}
