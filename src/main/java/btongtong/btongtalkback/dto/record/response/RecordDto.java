package btongtong.btongtalkback.dto.record.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecordDto {
    private Long id;
    private String name;
    private String question;
    private LocalDateTime datetime;
    private Boolean progress;

    public RecordDto(String name, Long id, String question, LocalDateTime datetime, Boolean progress) {
        this.id = id;
        this.name = name;
        this.question = question;
        this.datetime = datetime;
        this.progress = progress;
    }
}
