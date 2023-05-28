package com.zerobase.daangnmarketclone.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoMapClient {

    @Value("${kakao.api.key}")
    public String apiKey;

    public static final String HTTP_URL = "https://dapi.kakao.com/v2/local/geo/coord2address.json";

    private final RestTemplate restTemplate;

    // 위도, 경도를 가지고 지역 주소로 반환
    public ApiAddressResponse getAddressByPosition(Double latitude, Double longitude) {

        HttpHeaders headers = createHeaders();

        UriComponentsBuilder uri = createUri(latitude, longitude);

        HttpEntity<String> entity = createHttpEntity(headers);

        ResponseEntity<ApiResponseDto<ApiTotalAddressResponse>> response = restTemplate.exchange(
            uri.toUriString(),
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<>() {
            }
        );

        log.info("응답 결과 : {}", response.getBody());

        return response.getBody().getDocuments().get(0).getAddress();

    }

    private static HttpEntity<String> createHttpEntity(HttpHeaders headers) {
        return new HttpEntity<>(headers);
    }

    private UriComponentsBuilder createUri(Double latitude, Double longitude) {
        return UriComponentsBuilder
            .fromHttpUrl(HTTP_URL)
            .queryParam("x", longitude)
            .queryParam("y", latitude);
    }

    private HttpHeaders createHeaders() {
        // 카카오 api key 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, apiKey);

        return headers;
    }


}
