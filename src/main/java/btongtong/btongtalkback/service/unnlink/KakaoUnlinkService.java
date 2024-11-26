package btongtong.btongtalkback.service.unnlink;

import btongtong.btongtalkback.constant.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static btongtong.btongtalkback.constant.Provider.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;

@Component
@RequiredArgsConstructor
public class KakaoUnlinkService implements OauthUnlinkService{
    private final RestTemplate restTemplate;

    @Value("${spring.kakao.unlink.url}")
    private String kakaoUnlinkUrl;
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public void unlink(String token) {
        HttpEntity<String> requestEntity = new HttpEntity<>(null, getHeaders(token));
        restTemplate.postForEntity(kakaoUnlinkUrl, requestEntity, String.class);
    }

    private HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        headers.add(AUTHORIZATION, BEARER_PREFIX + token);
        return headers;
    }

    @Override
    public Provider getProvider() {
        return KAKAO;
    }
}
