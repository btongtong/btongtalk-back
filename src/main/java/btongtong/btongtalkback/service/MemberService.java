package btongtong.btongtalkback.service;

import btongtong.btongtalkback.constant.ErrorCode;
import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.dto.auth.ReissueDto;
import btongtong.btongtalkback.dto.member.response.MemberDto;
import btongtong.btongtalkback.handler.exception.CustomException;
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
                .orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
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

            // refresh RTR
            Member member = getMemberById(Long.parseLong(id));
            if(!member.getRefreshToken().equals(refresh)) {
                throw new CustomException(ErrorCode.TOKEN_NOT_VALID);
            }

            String newAccess = jwtUtil.createAccessToken(id, role);
            String newRefresh = jwtUtil.createRefreshToken(id, role);
            ResponseCookie cookie = jwtUtil.createResponseCookie(HttpHeaders.AUTHORIZATION, newRefresh, jwtUtil.refreshExpireSecond);

            member.updateRefreshToken(newRefresh);
            return new ReissueDto(newAccess, cookie);

        } catch (Exception e) {
            throw new CustomException(ErrorCode.TOKEN_NOT_VALID);
        }
    }
}
