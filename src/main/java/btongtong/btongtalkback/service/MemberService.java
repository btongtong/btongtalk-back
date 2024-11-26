package btongtong.btongtalkback.service;

import btongtong.btongtalkback.constant.Provider;
import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.dto.auth.ReissueDto;
import btongtong.btongtalkback.dto.member.response.MemberDto;
import btongtong.btongtalkback.handler.exception.CustomException;
import btongtong.btongtalkback.service.unnlink.OauthUnlinkService;
import btongtong.btongtalkback.util.JwtUtil;
import btongtong.btongtalkback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static btongtong.btongtalkback.constant.ErrorCode.*;
import static btongtong.btongtalkback.constant.Token.*;
import static org.springframework.http.HttpHeaders.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final List<OauthUnlinkService> unlinkServices;

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(CONTENT_NOT_FOUND));
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
        String token = member.getOauthAccessToken();
        Provider provider = Provider.fromString(member.getProvider());

        OauthUnlinkService service = getUnlinkService(provider);

        service.unlink(token);
        memberRepository.delete(member);
    }

    private OauthUnlinkService getUnlinkService(Provider provider) {
        return unlinkServices
                .stream()
                .filter(s -> s.getProvider().equals(provider))
                .findFirst().orElseThrow(() -> new CustomException(NOT_EXIST_PROVIDER));
    }

    public ReissueDto reissue(String refresh) {
        Member member = getMemberFromToken(refresh);
        validateRefreshToken(refresh, member);

        String newAccess = createNewAccessToken(member);
        String newRefresh = createNewRefreshToken(member);
        ResponseCookie cookie = createResponseCookie(newRefresh);

        member.updateRefreshToken(newRefresh);
        return new ReissueDto(newAccess, cookie);
    }

    private Member getMemberFromToken (String token) {
        validateToken(token);
        String id = jwtUtil.getId(token);
        return getMemberById(Long.parseLong(id));
    }

    private void validateToken(String token) {
        if (!jwtUtil.isValid(token)) {
            throw new CustomException(TOKEN_NOT_VALID);
        }
    }

    private void validateRefreshToken(String token, Member member) {
        if (!member.getRefreshToken().equals(token)) {
            throw new CustomException(TOKEN_NOT_VALID);
        }
    }

    private String createNewAccessToken(Member member) {
        return jwtUtil.createAccessToken(String.valueOf(member.getId()), String.valueOf(member.getRole()));
    }

    private String createNewRefreshToken(Member member) {
        return jwtUtil.createRefreshToken(String.valueOf(member.getId()), String.valueOf(member.getRole()));
    }

    private ResponseCookie createResponseCookie(String newRefresh) {
        return jwtUtil.createResponseCookie(AUTHORIZATION, newRefresh, REFRESH.getExpireTime());
    }
}
