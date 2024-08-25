package btongtong.btongtalkback.dto.member.response;

import btongtong.btongtalkback.domain.Member;
import lombok.Getter;

@Getter
public class MemberDto {
    private String name;
    private String email;
    private String profileImg;

    public MemberDto(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.profileImg = member.getProfileImg();
    }
}
