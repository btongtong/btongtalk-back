package btongtong.btongtalkback.service;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.dto.auth.ReissueDto;
import btongtong.btongtalkback.dto.member.response.MemberDto;
import btongtong.btongtalkback.util.JwtUtil;
import btongtong.btongtalkback.repository.MemberRepository;
import btongtong.btongtalkback.util.OauthUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtil jwtUtil;
    private final OauthUtil oauthUtil;
    private final MemberRepository memberRepository;

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public MemberDto getMember(Long memberId) {
        Member member = getMemberById(memberId);
        return new MemberDto(member);
    }

    public void updateRefreshToken(Long memberId, String refreshToken) {
        Member member = getMemberById(memberId);
        member.updateRefreshToken(refreshToken);
    }

    public void logout (Long memberId) {
        updateRefreshToken(memberId, "");
    }

    public void withdraw(Long memberId) {
        Member member = getMemberById(memberId);
        oauthUtil.unlinkAccount(member);
        memberRepository.delete(member);
    }

    public ReissueDto reissue(String refresh) {
        try {
            jwtUtil.isValid(refresh);
            String id = jwtUtil.getId(refresh);
            String role = jwtUtil.getRole(refresh);

            String newAccess = jwtUtil.createAccessToken(id, role);
            String newRefresh = jwtUtil.createRefreshToken(id, role);
            ResponseCookie cookie = jwtUtil.createResponseCookie(HttpHeaders.AUTHORIZATION, newRefresh, jwtUtil.refreshExpireSecond);

            updateRefreshToken(Long.parseLong(id), newRefresh);
            return new ReissueDto(newAccess, cookie);

        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("refresh token expired");
        }
    }
}
