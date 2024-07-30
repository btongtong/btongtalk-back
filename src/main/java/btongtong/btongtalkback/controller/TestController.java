package btongtong.btongtalkback.controller;

import btongtong.btongtalkback.dto.MemberDto;
import btongtong.btongtalkback.service.WithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final WithdrawService withdrawService;
    @GetMapping("/withdraw")
    public String withdraw(@AuthenticationPrincipal MemberDto memberDto) {
        return withdrawService.withdraw(memberDto.getId());
    }
}
