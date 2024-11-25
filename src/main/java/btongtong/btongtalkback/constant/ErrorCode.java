package btongtong.btongtalkback.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SE", "Sever error."),
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CE", "Content doesn't exist."),
    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "TE", "Token is not valid."),
    DUPLICATE_CONTENT(HttpStatus.CONFLICT, "DE", "Duplicated content."),
    NOT_EXIST_PROVIDER(HttpStatus.BAD_REQUEST, "NP", "No such Provider.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
