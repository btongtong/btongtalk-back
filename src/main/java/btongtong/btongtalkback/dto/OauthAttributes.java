package btongtong.btongtalkback.dto;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OauthAttributes {
    private Map<String, Object> attributes;
    private String oauthKey;
    private String provider;
    private String email;
    private String name;
    private Role role;
    private String accessToken;

    @Builder
    public OauthAttributes(Map<String, Object> attributes, String oauthKey, String provider, String email, String name, Role role, String accessToken) {
        this.attributes = attributes;
        this.oauthKey = oauthKey;
        this.provider = provider;
        this.email = email;
        this.name = name;
        this.role = role;
        this.accessToken = accessToken;
    }

    public static OauthAttributes of(String registrationId, Map<String, Object> attributes, String accessToken) {
        switch (registrationId) {
            case "naver":
                return ofNaver(registrationId, attributes, accessToken);
            case "kakao":
                return ofKakao(registrationId, attributes, accessToken);
        }

        return null;
    }

    public static OauthAttributes ofNaver(String provider, Map<String, Object> attributes, String accessToken) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OauthAttributes.builder()
                .attributes(new HashMap<>(attributes))  // map 수정 가능하도록 새로 만들기 (원래는 unmodifiableMap 형태)
                .oauthKey((String) response.get("id"))
                .provider(provider)
                .email((String) response.get("email"))
                .name((String) response.get("name"))
                .role(Role.USER)
                .accessToken(accessToken)
                .build();
    }

    public static OauthAttributes ofKakao(String provider, Map<String, Object> attributes, String accessToken) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return OauthAttributes.builder()
                .attributes(new HashMap<>(attributes))
                .oauthKey(attributes.get("id").toString())
                .provider(provider)
                .email((String) kakaoAccount.get("email"))
                .name((String) profile.get("nickname"))
                .role(Role.USER)
                .accessToken(accessToken)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .nickname(name)
                .role(role)
                .oauthKey(oauthKey)
                .provider(provider)
                .oauthAccessToken(accessToken)
                .build();
    }

    public void updateAttributes(Long memberId) {
        this.attributes.put("id", memberId);
    }

}
