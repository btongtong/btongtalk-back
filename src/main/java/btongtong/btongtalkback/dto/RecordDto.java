package btongtong.btongtalkback.dto;

import btongtong.btongtalkback.domain.RecordStatus;
import lombok.Getter;

@Getter
public class RecordDto {
    private Long flashcardId;
    private RecordStatus status;

    public RecordDto(Long flashcard_id, RecordStatus status) {
        this.flashcardId = flashcard_id;
        this.status = status;
    }
}
