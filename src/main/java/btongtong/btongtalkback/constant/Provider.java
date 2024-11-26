package btongtong.btongtalkback.constant;

import btongtong.btongtalkback.handler.exception.CustomException;
import lombok.Getter;

@Getter
public enum Provider {
    NAVER("naver", "id", "profile_image", "email", "name"),
    KAKAO("kakao", "id", "profile_image_url", "email", "nickname");

    private String type;
    private String id;
    private String profileImage;
    private String email;
    private String name;

    Provider(String type, String id, String profileImage, String email, String name) {
        this.type = type;
        this.id = id;
        this.profileImage = profileImage;
        this.email = email;
        this.name = name;
    }

    public static Provider fromString (String provider) {
        try {
            return Provider.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.NOT_EXIST_PROVIDER);
        }
    }
}
