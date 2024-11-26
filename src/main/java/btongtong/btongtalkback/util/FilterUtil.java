package btongtong.btongtalkback.util;

import btongtong.btongtalkback.constant.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.MediaType.*;

@Component
public class FilterUtil {
    private static final String ERROR_MSG = "{\"code\": \"%s\", \"message\": \"%s\"}";

    public void makeErrorMessage(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.getStatus().value());
        response.getWriter().write(String.format(ERROR_MSG, errorCode.getCode(), errorCode.getMessage()));
    }
}
