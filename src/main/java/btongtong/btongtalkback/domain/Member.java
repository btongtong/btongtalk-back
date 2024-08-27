package btongtong.btongtalkback.domain;

import btongtong.btongtalkback.constant.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @Tsid
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String name;
    private String profileImg;
    private String oauthKey;
    private String provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String oauthAccessToken;
    private String refreshToken;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    // 생성자
    @Builder
    public Member(String email, String name, String profileImg, String oauthKey, String provider, Role role, String oauthAccessToken, String refreshToken) {
        this.email = email;
        this.name = name;
        this.profileImg = profileImg;
        this.oauthKey = oauthKey;
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
