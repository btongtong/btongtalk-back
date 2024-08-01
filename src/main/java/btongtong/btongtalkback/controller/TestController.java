package btongtong.btongtalkback.controller;

import btongtong.btongtalkback.dto.MemberDto;
import btongtong.btongtalkback.jwt.JwtUtil;
import btongtong.btongtalkback.service.TokenService;
import btongtong.btongtalkback.service.WithdrawService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TokenService tokenService;
    private final WithdrawService withdrawService;

    @GetMapping("/withdraw")
    public String withdraw(@AuthenticationPrincipal MemberDto memberDto) {
        return withdrawService.withdraw(memberDto.getId());
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> resissue(@CookieValue("Authorization") String refresh) {
        return tokenService.reissue(refresh);
    }
}
