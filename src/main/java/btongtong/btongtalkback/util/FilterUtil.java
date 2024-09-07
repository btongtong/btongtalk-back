package btongtong.btongtalkback.util;

import btongtong.btongtalkback.constant.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FilterUtil {
    private final String contentType = "application/json";
    public void makeErrorMessage(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType(contentType);
        response.setStatus(errorCode.getStatus().value());
        response.getWriter().write("{\"code\": \"" + errorCode.getCode() + "\", \"message\": \"" + errorCode.getMessage() + "\"}");
    }
}
