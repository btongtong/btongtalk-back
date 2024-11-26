package btongtong.btongtalkback.util;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static btongtong.btongtalkback.constant.Payload.*;
import static btongtong.btongtalkback.constant.Token.*;
import static org.springframework.http.HttpHeaders.*;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}")String secretKey) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getId(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                    .get(ID.getName(), String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String getRole(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                    .get(ROLE.getName(), String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String getType(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                    .get(TYPE.getName(), String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isValid(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String createJwt(String type, String id, String role, Long expiredMs) {
        return Jwts.builder()
                .claim(TYPE.getName(), type)
                .claim(ID.getName(), id)
                .claim(ROLE.getName(), role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String createAccessToken(String id, String role) {
        return createJwt(ACCESS.getType(), id, role, ACCESS.getExpireMsTime());
    }

    public String createRefreshToken(String id, String role) {
        return createJwt(REFRESH.getType(), id, role, REFRESH.getExpireMsTime());
    }

    public Cookie createCookie(String name, String value, int expiredS) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiredS);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    public ResponseCookie createResponseCookie(String name, String value, int expiredS) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .path("/")
                .maxAge(expiredS)
                .build();
        return cookie;
    }

    public ResponseCookie createExpiredCookie() {
        return createResponseCookie(AUTHORIZATION, null, 0);
    }
}
