package com.zerobase.daangnmarketclone.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        UsernamePasswordAuthenticationToken token;
        // JSON 형태 -> ObjectMapper 로 매핑
        try {
            LogInRequestDto logInRequestDto
                = new ObjectMapper().readValue(request.getInputStream(), LogInRequestDto.class);
            token = new UsernamePasswordAuthenticationToken(logInRequestDto.getEmail(),
                logInRequestDto.getPassword());
        } catch (IOException e) {
            throw new RuntimeException("InputNotFoundException 발생");
        }

        setDetails(request, token);

        return this.getAuthenticationManager().authenticate(token);

    }
}
