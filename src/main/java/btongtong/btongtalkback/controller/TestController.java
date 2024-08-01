package btongtong.btongtalkback.controller;

import btongtong.btongtalkback.dto.MemberDto;
import btongtong.btongtalkback.service.MemberService;
import btongtong.btongtalkback.service.TokenService;
import btongtong.btongtalkback.service.WithdrawService;
import lombok.RequiredArgsConstructor;
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
    private final MemberService memberService;

    @GetMapping("/withdraw")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal MemberDto memberDto) {
        return withdrawService.withdraw(memberDto.getId());
    }

    @GetMapping("/signout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal MemberDto memberDto) {
        return memberService.logout(memberDto.getId());
    }

    @GetMapping("/my")
    public String my() {
        return "ok";
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> resissue(@CookieValue("Authorization") String refresh) {
        return tokenService.reissue(refresh);
    }
}
