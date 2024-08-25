package btongtong.btongtalkback.dto.flashcard.response;

import btongtong.btongtalkback.constant.RecordStatus;
import lombok.Getter;

import java.util.Objects;

@Getter
public class SearchFlashcardDto {
    private String categoryName;
    private Long flashcardId;
    private String question;
    private String status;

    public SearchFlashcardDto(String categoryName, Long flashcardId, String question, RecordStatus status) {
        this.categoryName = categoryName;
        this.flashcardId = flashcardId;
        this.question = question;
        this.status = Objects.requireNonNullElse(status, RecordStatus.NORECORD).name();
    }
}
