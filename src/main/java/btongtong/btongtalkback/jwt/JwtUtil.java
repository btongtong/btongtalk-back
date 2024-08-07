package btongtong.btongtalkback.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}")String secretKey) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public String getType(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("type", String.class);
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
                .claim("type", type)
                .claim("id", id)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String createAccessToken(String id, String role) {
        return createJwt("access", id, role, 60*60*1000L);
    }

    public String createRefreshToken(String id, String role) {
        return createJwt("refresh", id, role, 60*60*24*1000L);
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
}
