package btongtong.btongtalkback.dto.auth;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.constant.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OauthAttributes {
    private Map<String, Object> attributes;
    private String oauthKey;
    private String provider;
    private String profileImg;
    private String email;
    private String name;
    private Role role;

    @Builder
    public OauthAttributes(Map<String, Object> attributes, String oauthKey, String provider, String profileImg, String email, String name, Role role) {
        this.attributes = attributes;
        this.oauthKey = oauthKey;
        this.provider = provider;
        this.profileImg = profileImg;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public static OauthAttributes of(String registrationId, Map<String, Object> attributes) {
        switch (registrationId) {
            case "naver":
                return ofNaver(registrationId, attributes);
            case "kakao":
                return ofKakao(registrationId, attributes);
        }

        return null;
    }

    public static OauthAttributes ofNaver(String provider, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OauthAttributes.builder()
                .attributes(new HashMap<>(attributes))  // map 수정 가능하도록 새로 만들기 (원래는 unmodifiableMap 형태)
                .oauthKey((String) response.get("id"))
                .provider(provider)
                .profileImg((String) response.get("profile_image"))
                .email((String) response.get("email"))
                .name((String) response.get("name"))
                .role(Role.USER)
                .build();
    }

    public static OauthAttributes ofKakao(String provider, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return OauthAttributes.builder()
                .attributes(new HashMap<>(attributes))
                .oauthKey(attributes.get("id").toString())
                .provider(provider)
                .email((String) kakaoAccount.get("email"))
                .name((String) profile.get("nickname"))
                .profileImg((String) profile.get("profile_image_url"))
                .role(Role.USER)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .profileImg(profileImg)
                .email(email)
                .name(name)
                .role(role)
                .oauthKey(oauthKey)
                .provider(provider)
                .build();
    }

    public void updateAttributes(String key, String value) {
        this.attributes.put(key, value);
    }

}
