package com.zerobase.daangnmarketclone.configuration.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";

    // 로그인 성공 시
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {

        UserDetails userDetails = ((UserDetails) authentication.getPrincipal());

        // UserDetails 로 JWT 생성
        String token = TokenUtils.generateJwt(userDetails);

        response.addHeader(AUTH_TOKEN_HEADER, token);

    }

}
