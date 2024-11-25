package btongtong.btongtalkback.service.unnlink;

import btongtong.btongtalkback.constant.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NaverUnlinkService implements OauthUnlinkService{
    private final RestTemplate restTemplate;

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
        return String.format("grant_type=delete&client_id=%s&client_secret=%s&access_token=%s",
                naverClientId, naverClientSecret, token);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    @Override
    public Provider getProvider() {
        return Provider.NAVER;
    }
}
