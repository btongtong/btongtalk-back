package btongtong.btongtalkback.controller;

import btongtong.btongtalkback.dto.auth.AuthDto;
import btongtong.btongtalkback.dto.auth.ReissueDto;
import btongtong.btongtalkback.dto.member.response.MemberDto;
import btongtong.btongtalkback.service.MemberService;
import btongtong.btongtalkback.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @GetMapping("/member")
    public ResponseEntity<?> member(@AuthenticationPrincipal AuthDto memberDto) {
        MemberDto response = memberService.getMember(memberDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal AuthDto authDto) {
        memberService.withdraw(authDto.getId());
        ResponseCookie cookie = jwtUtil.createExpiredCookie();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal AuthDto authDto) {
        memberService.logout(authDto.getId());
        ResponseCookie cookie = jwtUtil.createExpiredCookie();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> resissue(@CookieValue(value = "Authorization", required = false) String refresh) {
        ReissueDto response = memberService.reissue(refresh);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, response.getCookie().toString())
                .body(response.getAccessToken());
    }
}
