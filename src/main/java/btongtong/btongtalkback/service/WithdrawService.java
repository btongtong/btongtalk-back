package btongtong.btongtalkback.service;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.jwt.JwtUtil;
import btongtong.btongtalkback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WithdrawService {
    private final MemberRepository memberRepository;

    @Value("${spring.naver.unlink.url}")
    private String naverUnlinkUrl;
    @Value("${spring.kakao.unlink.url}")
    private String kakaoUnlinkUrl;
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    @Transactional
    public ResponseEntity withdraw(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        String accessToken = member.getOauthAccessToken();
        String provider = member.getProvider();

        if(provider.equals("naver")) {
            naverUnlink(accessToken);
        } else if(provider.equals("kakao")) {
            kakaoUnlink(accessToken);
        }

        memberRepository.delete(member);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public void naverUnlink(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String requestBody = "grant_type=delete&client_id=" + naverClientId + "&client_secret=" + naverClientSecret + "&access_token=" + accessToken + "&service_provider=NAVER";

        ResponseEntity<String> response = restTemplate.postForEntity(naverUnlinkUrl, new HttpEntity<String>(requestBody, headers), String.class);
    }

    public void kakaoUnlink(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        ResponseEntity<String> response = restTemplate.postForEntity(kakaoUnlinkUrl, new HttpEntity<String>(null, headers), String.class);
    }

}
