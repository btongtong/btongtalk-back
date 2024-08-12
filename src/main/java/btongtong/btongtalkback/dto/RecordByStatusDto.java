package btongtong.btongtalkback.dto;

import btongtong.btongtalkback.domain.RecordStatus;
import lombok.Getter;

@Getter
public class RecordByStatusDto {
    private String name;
    private Long id;
    private String question;
    private String status;

    public RecordByStatusDto(String name, Long id, String question, RecordStatus status) {
        this.id = id;
        this.name = name;
        this.question = question;
        this.status = status.name();
    }
}
