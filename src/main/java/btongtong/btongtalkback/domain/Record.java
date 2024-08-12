package btongtong.btongtalkback.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Record(Member member, Flashcard flashcard, RecordStatus status) {
        this.member = member;
        this.flashcard = flashcard;
        this.status = status;
        this.recordDate = LocalDateTime.now();

        if(member != null) {
            member.getRecords().add(this);
        }
        if(flashcard != null) {
            flashcard.getRecords().add(this);
        }
    }

    public void updateStatus(RecordStatus status) {
        this.status = status;
        this.recordDate = LocalDateTime.now();
    }
}
