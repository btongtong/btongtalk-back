package btongtong.btongtalkback.dto.record.response;

import btongtong.btongtalkback.constant.RecordStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecordDto {
    private Long id;
    private String name;
    private String question;
    private LocalDateTime datetime;
    private RecordStatus status;

    public RecordDto(String name, Long id, String question, LocalDateTime datetime, RecordStatus status) {
        this.id = id;
        this.name = name;
        this.question = question;
        this.datetime = datetime;
        this.status = status;
    }
}
