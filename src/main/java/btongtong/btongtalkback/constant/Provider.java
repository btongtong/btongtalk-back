package btongtong.btongtalkback.constant;

public enum Provider {
    NAVER, KAKAO;

    public static Provider fromString (String provider) {
        try {
            return Provider.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException("Unsupported provider: " + provider);
        }
    }
}
