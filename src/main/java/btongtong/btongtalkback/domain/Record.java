package btongtong.btongtalkback.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Record {
    @Id @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_id")
    private Flashcard flashcard;

    @Enumerated(EnumType.STRING)
    private RecordStatus status;

    private LocalDateTime recordDate;
}
