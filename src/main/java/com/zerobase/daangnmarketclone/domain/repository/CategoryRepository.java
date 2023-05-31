package com.zerobase.daangnmarketclone.domain.repository;

import com.zerobase.daangnmarketclone.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
