package btongtong.btongtalkback.handler;

import btongtong.btongtalkback.constant.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if((boolean)request.getAttribute("exception")) {
            makeErrorMessage(response);
        }
    }

    private void makeErrorMessage(HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        response.setStatus(ErrorCode.TOKEN_NOT_VALID.getStatus().value());
        response.getWriter().write("{\"code\": \"" + ErrorCode.TOKEN_NOT_VALID.getCode() + "\", \"message\": \"" + ErrorCode.TOKEN_NOT_VALID.getMessage() + "\"}");
    }
}
