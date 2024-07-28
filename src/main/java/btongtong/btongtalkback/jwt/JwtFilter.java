package btongtong.btongtalkback.jwt;

import btongtong.btongtalkback.dto.MemberDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;
        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        if(authorization == null) {
            // 예외
            filterChain.doFilter(request, response);

            return;
        }

        String token = authorization;

        if(jwtUtil.isExpired(token)) {
            // 예외
            filterChain.doFilter(request, response);

            return;
        }

        String id = jwtUtil.getId(token);
        String role = jwtUtil.getRole(token);

        MemberDto memberDto = new MemberDto(Long.parseLong(id), role);

        Authentication authToken = new UsernamePasswordAuthenticationToken(memberDto, null, Collections.singleton(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
