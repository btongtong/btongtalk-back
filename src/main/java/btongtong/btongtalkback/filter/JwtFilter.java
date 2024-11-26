package btongtong.btongtalkback.filter;

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

import static btongtong.btongtalkback.constant.Attribute.EXCEPTION;
import static btongtong.btongtalkback.constant.ErrorCode.*;
import static btongtong.btongtalkback.constant.Token.*;
import static org.springframework.http.HttpHeaders.*;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(isInvalidateToken(request)) {
            request.setAttribute(EXCEPTION.getName(), TOKEN_NOT_VALID);
            filterChain.doFilter(request, response);
            return;
        }

        authenticateUser(request);
        filterChain.doFilter(request, response);
    }

    private boolean isInvalidateToken(HttpServletRequest request) {
        String token = getToken(request);
        return token == null || !jwtUtil.isValid(token) || !ACCESS.getType().equals(jwtUtil.getType(token));
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
        return request.getHeader(AUTHORIZATION);
    }
}
