package btongtong.btongtalkback.constant;

public enum Payload {
    ID("id"),
    ROLE("role"),
    TYPE("type");

    private String name;

    Payload(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
