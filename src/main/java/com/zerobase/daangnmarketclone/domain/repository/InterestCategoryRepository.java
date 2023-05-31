package com.zerobase.daangnmarketclone.domain.repository;

import com.zerobase.daangnmarketclone.domain.entity.InterestCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestCategoryRepository extends JpaRepository<InterestCategory, Long> {

    Optional<InterestCategory> findByIdAndUserId(Long interestCategoryId, Long userId);

}
