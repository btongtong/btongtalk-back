package btongtong.btongtalkback.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String nickname;
    private String profileImg;

    private String oauthAccessToken;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @Builder
    public Member(String email, String nickname, String profileImg, String oauthAccessToken) {
        this.email = email;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.oauthAccessToken = oauthAccessToken;
    }
}
