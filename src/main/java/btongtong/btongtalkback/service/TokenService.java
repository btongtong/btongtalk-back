package btongtong.btongtalkback.service;

import btongtong.btongtalkback.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

        String newAccess = jwtUtil.createAccessToken(id, role);
        String newRefresh = jwtUtil.createRefreshToken(id, role);
        ResponseCookie cookie = jwtUtil.createResponseCookie("Authorization", newRefresh, 60*60*24);

        memberService.updateRefreshToken(id, newRefresh);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(newAccess);
    }

}
