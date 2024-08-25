package btongtong.btongtalkback.dto.auth;

import lombok.Getter;
import org.springframework.http.ResponseCookie;

@Getter
public class ReissueDto {
    private String accessToken;
    private ResponseCookie cookie;

    public ReissueDto(String accessToken, ResponseCookie cookie) {
        this.accessToken = accessToken;
        this.cookie = cookie;
    }
}
