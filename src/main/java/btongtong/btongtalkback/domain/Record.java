package btongtong.btongtalkback.domain;

import btongtong.btongtalkback.constant.RecordStatus;
import com.github.f4b6a3.tsid.TsidCreator;
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
    @Id
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

    private Boolean progress;
    private LocalDateTime recordDate;

    @PrePersist
    public void setId() {
        if (this.id == null) {
            this.id = TsidCreator.getTsid().toLong();
        }
    }

    @Builder
    public Record(Member member, Flashcard flashcard, RecordStatus status, Boolean progress) {
        this.member = member;
        this.flashcard = flashcard;
        this.status = status;
        this.recordDate = LocalDateTime.now();
        this.progress = progress;

        if(member != null) {
            member.getRecords().add(this);
        }
        if(flashcard != null) {
            flashcard.getRecords().add(this);
        }
    }

    public void updateStatus(RecordStatus status) {
        this.status = status;
        this.progress = true;
        this.recordDate = LocalDateTime.now();
    }

}
