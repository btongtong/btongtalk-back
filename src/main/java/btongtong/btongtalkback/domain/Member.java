package btongtong.btongtalkback.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String nickname;
    private String profileImg;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Record> records = new ArrayList<>();

}
