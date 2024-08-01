package btongtong.btongtalkback.handler;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.dto.MemberDto;
import btongtong.btongtalkback.jwt.JwtUtil;
import btongtong.btongtalkback.repository.MemberRepository;
import btongtong.btongtalkback.service.MemberService;
import btongtong.btongtalkback.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String id = ((DefaultOAuth2User) authentication.getPrincipal()).getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        String refreshToken = jwtUtil.createJwt("refresh", id, role, 60*60*1000L);

        memberService.updateRefreshToken(id, refreshToken);

        response.addCookie(jwtUtil.createCookie("Authorization", refreshToken, 60*60*24));

        response.sendRedirect("http://localhost:3000/oauth");
    }
}
