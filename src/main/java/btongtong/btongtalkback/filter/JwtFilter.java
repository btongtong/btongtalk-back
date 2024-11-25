package btongtong.btongtalkback.filter;

import btongtong.btongtalkback.constant.ErrorCode;
import btongtong.btongtalkback.dto.auth.AuthDto;
import btongtong.btongtalkback.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
    private final String authorization = "Authorization";
    private final String exception = "exception";
    private final String access = "access";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isInvalidateToken(request)) {
            request.setAttribute(exception, ErrorCode.TOKEN_NOT_VALID);
            filterChain.doFilter(request, response);
            return;
        }

        authenticateUser(request);
        filterChain.doFilter(request, response);
    }

    private boolean isInvalidateToken(HttpServletRequest request) {
        String token = getToken(request);
        return token == null || !jwtUtil.isValid(token) || !access.equals(jwtUtil.getType(token));
    }

    private void authenticateUser(HttpServletRequest request) {
        String token = getToken(request);
        String id = jwtUtil.getId(token);
        String role = jwtUtil.getRole(token);
        setAuthentication(new AuthDto(Long.parseLong(id), role));
    }

    private void setAuthentication(AuthDto memberDto) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(memberDto,
                null,
                Collections.singleton(new SimpleGrantedAuthority(memberDto.getRole())));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String getToken(HttpServletRequest request) {
        return request.getHeader(authorization);
    }
}
