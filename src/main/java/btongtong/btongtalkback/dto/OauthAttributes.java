package btongtong.btongtalkback.dto;

import btongtong.btongtalkback.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OauthAttributes {
    private Map<String, Object> attributes;
    private String userNameAttributeName;
    private String email;
    private String name;
    private String profileImg;

    private String accessToken;

    @Builder
    public OauthAttributes(Map<String, Object> attributes, String userNameAttributeName, String email, String name, String profileImg, String accessToken) {
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
        this.email = email;
        this.name = name;
        this.profileImg = profileImg;
        this.accessToken = accessToken;
    }


    public static OauthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        switch (registrationId) {
            case "naver":
                return ofNaver(userNameAttributeName, attributes);
            case "kakao":
                return ofKakao(userNameAttributeName, attributes);
        }

        return null;
    }

    public static OauthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OauthAttributes.builder()
                .email((String) response.get("email"))
                .name((String) response.get("name"))
                .profileImg((String) response.get("profile_image"))
                .attributes(attributes)
                .userNameAttributeName(userNameAttributeName)
                .build();
    }

    public static OauthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return OauthAttributes.builder()
                .email((String) kakaoAccount.get("email"))
                .name((String) profile.get("nickname"))
                .profileImg((String) profile.get("profile_image_url"))
                .attributes(attributes)
                .userNameAttributeName(userNameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .nickname(name)
                .profileImg(profileImg)
                .oauthAccessToken(accessToken)
                .build();
    }

}
