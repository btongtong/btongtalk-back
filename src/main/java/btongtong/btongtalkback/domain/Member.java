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
    private String oauthKey;
    private String provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String oauthAccessToken;
    private String refreshToken;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @Builder
    public Member(String email, String nickname, String provider, String oauthKey, Role role, String oauthAccessToken) {
        this.email = email;
        this.nickname = nickname;
        this.oauthKey = oauthKey;
        this.provider = provider;
        this.role = role;
        this.oauthAccessToken = oauthAccessToken;
    }

    public void updateOauthAccessToken(String oauthAccessToken) {
        this.oauthAccessToken = oauthAccessToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
