package com.zerobase.daangnmarketclone.configuration;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    // logging 또는 추가적인 작업이 필요할 경우 CustomInterceptor 추가
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
            .requestFactory(
                // 만약 connection pool 을 지원해야할 경우 HttpComponentsClientHttpRequestFactory 사용
                () -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
            .setConnectTimeout(Duration.ofMillis(5000)) // connection-timeout
            .setReadTimeout(Duration.ofMillis(5000))    // read-timeout
            // StringHttpMessageConverter 의 DEFAULT_CHARSET : ISO_8859_1
            .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
            .build();
    }

}
