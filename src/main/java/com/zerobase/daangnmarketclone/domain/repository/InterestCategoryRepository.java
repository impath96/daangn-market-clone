package com.zerobase.daangnmarketclone.domain.repository;

import com.zerobase.daangnmarketclone.domain.entity.InterestCategory;
import java.util.Optional;

public interface InterestCategoryRepository extends CommonRepository<InterestCategory, Long> {

    Optional<InterestCategory> findByIdAndUserId(Long interestCategoryId, Long userId);

}
