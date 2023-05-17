package com.zerobase.daangnmarketclone.configuration.security;

import com.zerobase.daangnmarketclone.domain.entity.user.UserRole;
import com.zerobase.daangnmarketclone.dto.UserDto;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

            UserDto userDto = new UserDto();
            userDto.setEmail(username);
            userDto.setRole(userRole);

            UserDetails userDetails = new UserDetailsImpl(UserDto.toEntity(userDto));

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
