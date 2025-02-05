package runnershigh.capstone.jwt.enums;

public enum AuthConstants {
    REFRESH_COOKIE_NAME("refresh_token"),
    AUTHORIZATION_HEADER("Authorization"),
    BEARER_PREFIX("Bearer ");

    private final String value;

    AuthConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
