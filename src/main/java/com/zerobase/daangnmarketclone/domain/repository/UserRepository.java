package com.zerobase.daangnmarketclone.domain.repository;

import com.zerobase.daangnmarketclone.domain.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends CommonRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("select u from User u left join fetch u.userRegions")
    Optional<User> findByEmailWithUserRegion(String email);



}
