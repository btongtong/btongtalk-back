package btongtong.btongtalkback.service;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void updateRefreshToken(String id, String refreshToken) {
        Member member = memberRepository.findById(Long.parseLong(id)).orElseThrow(IllegalArgumentException::new);
        member.updateRefreshToken(refreshToken);
    }
}
