package btongtong.btongtalkback.service.unnlink;

import btongtong.btongtalkback.constant.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static btongtong.btongtalkback.constant.Provider.*;
import static org.springframework.http.MediaType.*;

@Component
@RequiredArgsConstructor
public class NaverUnlinkService implements OauthUnlinkService{
    private final RestTemplate restTemplate;
    private static final String REQUEST_BODY = "grant_type=delete&client_id=%s&client_secret=%s&access_token=%s";

    @Value("${spring.naver.unlink.url}")
    private String naverUnlinkUrl;
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    @Override
    public void unlink(String token) {
        HttpEntity<String> requestEntity = new HttpEntity<>(getRequestBody(token), getHeaders());
        restTemplate.postForEntity(naverUnlinkUrl, requestEntity, String.class);
    }

    private String getRequestBody(String token) {
        return String.format(REQUEST_BODY, naverClientId, naverClientSecret, token);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        return headers;
    }

    @Override
    public Provider getProvider() {
        return NAVER;
    }
}
