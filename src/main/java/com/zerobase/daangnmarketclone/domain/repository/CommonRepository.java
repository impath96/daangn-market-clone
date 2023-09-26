package com.zerobase.daangnmarketclone.domain.repository;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface CommonRepository<T, ID> extends Repository<T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

}
