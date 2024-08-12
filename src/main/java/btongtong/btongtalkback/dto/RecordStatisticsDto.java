package btongtong.btongtalkback.dto;

import lombok.Getter;

@Getter
public class RecordStatisticsDto {
    private Long id;
    private String name;
    private Long total;
    private Long cnt;

    public RecordStatisticsDto(Long id, String name, Long total, Long cnt) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.cnt = cnt;
    }
}
