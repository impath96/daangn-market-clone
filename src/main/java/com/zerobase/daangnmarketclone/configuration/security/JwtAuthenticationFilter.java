package com.zerobase.daangnmarketclone.configuration.security;

import com.zerobase.daangnmarketclone.domain.entity.user.UserRole;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {

        String jwt = request.getHeader("X-AUTH-TOKEN");

        if (jwt == null) {
            chain.doFilter(request, response);
            return;
        }

        if (TokenUtils.isValid(jwt)) {

            String username = TokenUtils.extractUsername(jwt);
            UserRole userRole = TokenUtils.extractRole(jwt);

            // UserDetailsService를 통해서 가져오면 편하긴 한데 DB 조회가 1번 발생
            // 어떻게 하는게 좋을까?
            UserDetails userDetails = UserDetailsImpl.builder()
                .username(username)
                .authorities(List.of(new SimpleGrantedAuthority(userRole.getName())))
                .build();

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("authentication 객체 저장 완료 : {}", authentication);
        }
        chain.doFilter(request, response);
    }

}
