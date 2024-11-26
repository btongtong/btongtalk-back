package btongtong.btongtalkback.handler;

import btongtong.btongtalkback.constant.ErrorCode;
import btongtong.btongtalkback.dto.error.response.ErrorResponse;
import btongtong.btongtalkback.handler.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static btongtong.btongtalkback.constant.ErrorCode.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handle(CustomException e) {
        return  createErrorResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handle(Exception e) {
        return createErrorResponseEntity(INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode) {
        return new ResponseEntity<>(
                ErrorResponse.of(errorCode),
                errorCode.getStatus()
        );
    }
}
