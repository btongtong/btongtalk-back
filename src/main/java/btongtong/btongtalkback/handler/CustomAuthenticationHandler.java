package btongtong.btongtalkback.handler;

import btongtong.btongtalkback.constant.ErrorCode;
import btongtong.btongtalkback.util.FilterUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static btongtong.btongtalkback.constant.Attribute.*;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationHandler implements AuthenticationEntryPoint {
    private final FilterUtil filterUtil;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if(request.getAttribute(EXCEPTION.getName()) != null) {
            filterUtil.makeErrorMessage(response, (ErrorCode) request.getAttribute(EXCEPTION.getName()));
        }
    }
}
