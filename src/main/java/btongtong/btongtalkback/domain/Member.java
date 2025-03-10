package btongtong.btongtalkback.domain;

import btongtong.btongtalkback.constant.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.f4b6a3.tsid.TsidCreator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String name;
    private String profileImg;
    private String provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String oauthAccessToken;
    private String refreshToken;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @PrePersist
    public void setId() {
        if (this.id == null) {
            this.id = TsidCreator.getTsid().toLong();
        }
    }

    // 생성자
    @Builder
    public Member(String email, String name, String profileImg, String provider, Role role, String oauthAccessToken, String refreshToken) {
        this.email = email;
        this.name = name;
        this.profileImg = profileImg;
        this.provider = provider;
        this.role = role;
        this.oauthAccessToken = oauthAccessToken;
        this.refreshToken = refreshToken;
    }

    // 업데이트
    public void updateOauthAccessToken(String oauthAccessToken) {
        this.oauthAccessToken = oauthAccessToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateProfile(Member member) {
        this.email = member.email;
        this.name = member.name;
        this.profileImg = member.profileImg;
    }
}
