package btongtong.btongtalkback.constant;

import btongtong.btongtalkback.handler.exception.CustomException;

public enum Provider {
    NAVER, KAKAO;

    public static Provider fromString (String provider) {
        try {
            return Provider.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.NOT_EXIST_PROVIDER);
        }
    }
}
