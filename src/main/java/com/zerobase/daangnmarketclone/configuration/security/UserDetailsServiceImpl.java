package com.zerobase.daangnmarketclone.configuration.security;

import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.entity.user.UserRole;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
import com.zerobase.daangnmarketclone.exception.CustomException;
import com.zerobase.daangnmarketclone.exception.ErrorCode;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserDetailsImpl.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities(convert(user.getRole()))
            .build();
    }

    public Collection<? extends GrantedAuthority> convert(UserRole userRole) {
        return List.of(new SimpleGrantedAuthority(userRole.getName()));
    }

}
