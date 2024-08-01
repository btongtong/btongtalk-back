package btongtong.btongtalkback.service;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.jwt.JwtUtil;
import btongtong.btongtalkback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Transactional
    public void updateRefreshToken(String id, String refreshToken) {
        Member member = memberRepository.findById(Long.parseLong(id)).orElseThrow(IllegalArgumentException::new);
        member.updateRefreshToken(refreshToken);
    }

    @Transactional
    public ResponseEntity logout(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        member.updateRefreshToken("");

        ResponseCookie cookie = jwtUtil.createResponseCookie("Authorization", "", 0);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
