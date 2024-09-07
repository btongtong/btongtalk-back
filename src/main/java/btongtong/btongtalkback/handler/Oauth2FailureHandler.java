package btongtong.btongtalkback.handler;

import btongtong.btongtalkback.constant.ErrorCode;
import btongtong.btongtalkback.util.FilterUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Oauth2FailureHandler implements AuthenticationFailureHandler {
    private final FilterUtil filterUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(Objects.equals(exception.getMessage(), ErrorCode.DUPLICATE_CONTENT.getMessage())) {
            filterUtil.makeErrorMessage(response, ErrorCode.DUPLICATE_CONTENT);
        }
    }
}
