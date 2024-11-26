package btongtong.btongtalkback.handler;

import btongtong.btongtalkback.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static btongtong.btongtalkback.constant.Token.*;
import static org.springframework.http.HttpHeaders.*;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    @Value("${domain.name}")
    private String domainName;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String refreshToken = (String) ((DefaultOAuth2User) authentication.getPrincipal())
                .getAttributes()
                .get(REFRESH.getType());
        response.addCookie(jwtUtil.createCookie(AUTHORIZATION, refreshToken, REFRESH.getExpireTime()));

        response.sendRedirect(domainName + "/oauth");
    }
}
