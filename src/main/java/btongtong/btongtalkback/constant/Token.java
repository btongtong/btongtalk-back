package btongtong.btongtalkback.constant;

public enum Token {
    ACCESS("access", 60*60),
    REFRESH("refresh", 24*60*60);

    private String type;
    private int expireTime;

    Token(String type, int expireTime) {
        this.type = type;
        this.expireTime = expireTime;
    }

    public String getType() {
        return this.type;
    }

    public int getExpireTime() {
        return this.expireTime;
    }

    public long getExpireMsTime() {
        return this.expireTime * 1000L;
    }
}
