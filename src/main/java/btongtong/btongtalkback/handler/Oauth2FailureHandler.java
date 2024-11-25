package btongtong.btongtalkback.handler;

import btongtong.btongtalkback.constant.ErrorCode;
import btongtong.btongtalkback.util.FilterUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Oauth2FailureHandler implements AuthenticationFailureHandler {
    private final FilterUtil filterUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof OAuth2AuthenticationException oAuth2AuthenticationException) {
            handleOAuth2AuthenticationFailure(response, oAuth2AuthenticationException);
        }
    }

    private void handleOAuth2AuthenticationFailure(HttpServletResponse response,
                                                   OAuth2AuthenticationException exception) throws IOException {
        String code = exception.getError().getErrorCode();
        filterUtil.makeErrorMessage(response, ErrorCode.getErrorByCode(code));
    }
}
