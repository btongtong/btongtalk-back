package btongtong.btongtalkback.dto.error.response;

import btongtong.btongtalkback.constant.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private String code;
    private String message;

    private ErrorResponse(final ErrorCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    private ErrorResponse(final ErrorCode code, final String message) {
        this.code = code.getCode();
        this.message = message;
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final String message) {
        return new ErrorResponse(code, message);
    }
}
