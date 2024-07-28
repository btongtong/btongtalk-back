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
    private String userNameAttributeName;
    private String email;
    private String name;

    private String accessToken;

    @Builder
    public OauthAttributes(Map<String, Object> attributes, String userNameAttributeName, String email, String name, String accessToken) {
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
        this.email = email;
        this.name = name;
        this.accessToken = accessToken;
    }

    public OauthAttributes(){}

    public static OauthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        switch (registrationId) {
            case "naver":
                return ofNaver(userNameAttributeName, attributes, accessToken);
            case "kakao":
                return ofKakao(userNameAttributeName, attributes, accessToken);
        }

        return null;
    }

    public static OauthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        return OauthAttributes.builder()
                .email((String) ((Map) attributes.get("response")).get("email"))
                .name((String) ((Map) attributes.get("response")).get("name"))
                .attributes(new HashMap<>(attributes))  // map 수정 가능하도록 새로 만들기 (원래는 unmodifiableMap 형태)
                .userNameAttributeName(userNameAttributeName)
                .accessToken(accessToken)
                .build();
    }

    public static OauthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        return OauthAttributes.builder()
                .email((String) ((Map) attributes.get("kakao_account")).get("email"))
                .name((String) ((Map) attributes.get("profile")).get("nickname"))
                .attributes(new HashMap<>(attributes))
                .userNameAttributeName(userNameAttributeName)
                .accessToken(accessToken)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .nickname(name)
                .role(Role.USER.name())
                .oauthAccessToken(accessToken)
                .build();
    }

    public void updateAttributes(Long memberId) {
        this.attributes.put("id", memberId);
    }

}
