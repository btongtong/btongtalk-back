package btongtong.btongtalkback.service;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.jwt.JwtUtil;
import btongtong.btongtalkback.repository.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    public ResponseEntity reissue(String refresh) {
        if(refresh == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        try {
            jwtUtil.isValid(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        String id = jwtUtil.getId(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJwt("access", id, role, 60*60*1000L);
        String newRefresh = jwtUtil.createJwt("refresh", id, role, 60*60*24*1000L);

        ResponseCookie cookie = ResponseCookie.from("Authorization", newRefresh)
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 60 * 24)
                .build();

        memberService.updateRefreshToken(id, newRefresh);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(newAccess);
    }

}
