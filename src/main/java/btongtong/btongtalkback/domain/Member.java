package btongtong.btongtalkback.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.id.Tsid;
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
    @Tsid
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String nickname;
    private String profileImg;
    private String provider;
    private String role;
    private String oauthAccessToken;
    private String oauthRefreshToken;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @Builder
    public Member(String email, String nickname, String provider, String role, String oauthAccessToken) {
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
        this.role = role;
        this.oauthAccessToken = oauthAccessToken;
    }

    @Builder
    public Member(String email, String nickname, String role, String oauthAccessToken) {
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.oauthAccessToken = oauthAccessToken;
    }

    public void updateOauthAccessToken(String oauthAccessToken) {
        this.oauthAccessToken = oauthAccessToken;
    }

    public void updateOauthToken(String oauthAccessToken, String oauthRefreshToken) {
        this.oauthAccessToken = oauthAccessToken;
        this.oauthRefreshToken = oauthRefreshToken;
    }
}
