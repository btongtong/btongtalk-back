package btongtong.btongtalkback.dto.record.request;

import btongtong.btongtalkback.constant.RecordStatus;
import lombok.Getter;

@Getter
public class FlashcardIdAndStatusDto {
    private Long flashcardId;
    private RecordStatus status;

    public FlashcardIdAndStatusDto(Long flashcard_id, RecordStatus status) {
        this.flashcardId = flashcard_id;
        this.status = status;
    }
}
