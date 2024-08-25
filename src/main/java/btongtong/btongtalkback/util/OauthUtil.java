package btongtong.btongtalkback.util;

import btongtong.btongtalkback.constant.Provider;
import btongtong.btongtalkback.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OauthUtil {
    private static final String GRANT_TYPE_DELETE = "delete";
    private static final String BEARER_PREFIX = "Bearer ";

    private final RestTemplate restTemplate;

    @Value("${spring.naver.unlink.url}")
    private String naverUnlinkUrl;
    @Value("${spring.kakao.unlink.url}")
    private String kakaoUnlinkUrl;
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    public HttpHeaders createHeaders(MediaType contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        return headers;
    }

    public String createNaverReqeustBody(String accessToken) {
        return String.format("grant_type=%s&client_id=%s&client_secret=%s&access_token=%s",
                GRANT_TYPE_DELETE, naverClientId, naverClientSecret, accessToken);
    }

    public void unlink(String url, String requestBody, String authorizationHeader) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_FORM_URLENCODED);
        if(authorizationHeader != null) {
            headers.add(HttpHeaders.AUTHORIZATION, authorizationHeader);
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        restTemplate.postForEntity(url, requestEntity, String.class);
    }

    public void naverUnlink(String accessToken) {
        String requestBody = createNaverReqeustBody(accessToken);
        unlink(naverUnlinkUrl, requestBody, null);
    }

    public void kakaoUnlink(String accessToken) {
        String authorizationHeader = BEARER_PREFIX + accessToken;
        unlink(kakaoUnlinkUrl, null, authorizationHeader);
    }

    public void unlinkAccount(Member member) {
        String accessToken = member.getOauthAccessToken();
        Provider provider = Provider.fromString(member.getProvider());

        switch (provider) {
            case NAVER:
                naverUnlink(accessToken);
                break;
            case KAKAO:
                kakaoUnlink(accessToken);
                break;
            default:
                throw new IllegalArgumentException("Unsupported provider " + provider);
        }
    }
}
