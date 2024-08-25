package btongtong.btongtalkback.handler;

import btongtong.btongtalkback.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String refreshToken = (String) ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes().get("refresh");
        response.addCookie(jwtUtil.createCookie("Authorization", refreshToken, jwtUtil.refreshExpireSecond));

        response.sendRedirect("http://localhost:3000/oauth");
    }
}
