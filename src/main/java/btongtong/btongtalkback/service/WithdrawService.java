package btongtong.btongtalkback.service;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WithdrawService {

    private final MemberRepository memberRepository;

    @Value("${spring.naver.unlink.url}")
    private String NAVER_UNLINK_URL;
    @Value("${spring.kakao.unlink.url}")
    private String KAKAO_UNLINK_URL;
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    @Transactional
    public String withdraw(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        String accessToken = member.getOauthAccessToken();
        String provider = member.getProvider();

        if(provider.equals("naver")) {
            naverUnlink(accessToken);
        } else if(provider.equals("kakao")) {
            kakaoUnlink(accessToken);
        }

        memberRepository.delete(member);

        return "ok";
    }

    public String naverUnlink(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "grant_type=delete&client_id="+naverClientId+"&client_secret="+naverClientSecret+"&access_token=" + accessToken + "&service_provider=NAVER";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(NAVER_UNLINK_URL, request, String.class);
        System.out.println(response.getBody().toString());
        return "ok";
    }

    public String kakaoUnlink(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(KAKAO_UNLINK_URL, request, String.class);
        return "ok";
    }

}
