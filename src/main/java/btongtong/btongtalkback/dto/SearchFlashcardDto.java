package btongtong.btongtalkback.dto;

import btongtong.btongtalkback.domain.RecordStatus;
import lombok.Getter;

import java.util.Objects;

@Getter
public class SearchFlashcardDto {
    private String name;
    private Long id;
    private String question;
    private String status;

    public SearchFlashcardDto(String name, Long id, String question, RecordStatus status) {
        this.id = id;
        this.name = name;
        this.question = question;
        this.status = Objects.requireNonNullElse(status, RecordStatus.NORECORD).name();
    }
}
