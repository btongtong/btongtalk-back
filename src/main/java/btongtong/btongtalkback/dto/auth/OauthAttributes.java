package btongtong.btongtalkback.dto.auth;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.constant.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static btongtong.btongtalkback.constant.Provider.*;

@Getter
@Builder
public class OauthAttributes {
    private Map<String, Object> attributes;
    private String oauthKey;
    private String provider;
    private String profileImg;
    private String email;
    private String name;
    private Role role;

    public static OauthAttributes of(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equals(NAVER.getType())) {
            return ofNaver(registrationId, attributes);
        }
        if (registrationId.equals(KAKAO.getType())) {
            return ofKakao(registrationId, attributes);
        }

        return null;
    }

    public static OauthAttributes ofNaver(String provider, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OauthAttributes.builder()
                .attributes(new HashMap<>(attributes))  // map 수정 가능하도록 새로 만들기 (원래는 unmodifiableMap 형태)
                .oauthKey((String) response.get(NAVER.getId()))
                .provider(provider)
                .profileImg((String) response.get(NAVER.getProfileImage()))
                .email((String) response.get(NAVER.getEmail()))
                .name((String) response.get(NAVER.getName()))
                .role(Role.USER)
                .build();
    }

    public static OauthAttributes ofKakao(String provider, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return OauthAttributes.builder()
                .attributes(new HashMap<>(attributes))
                .oauthKey(attributes.get(KAKAO.getId()).toString())
                .provider(provider)
                .email((String) kakaoAccount.get(KAKAO.getEmail()))
                .profileImg((String) profile.get(KAKAO.getProfileImage()))
                .name((String) profile.get(KAKAO.getName()))
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
