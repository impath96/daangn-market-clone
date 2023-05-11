package com.zerobase.daangnmarketclone.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true) // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfiguration {

    // 비밀번호 암호화(Bcrypt 알고리즘)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 토큰을 활용할 경우 기본적으로 설정해줘야 하는 부분
        http.csrf().disable()
            // 토큰을 활용하는 경우 모든 요청에 대해 접근이 가능하도록 함
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            // 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설정하여 Session을 사용하지 않는다.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // form 기반의 로그인에 대해 비활성화 한다.
            .formLogin()
            .disable();

        return http.build();
    }
}
