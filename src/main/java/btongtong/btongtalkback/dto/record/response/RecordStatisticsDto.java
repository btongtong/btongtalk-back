package btongtong.btongtalkback.dto.record.response;

import lombok.Getter;

@Getter
public class RecordStatisticsDto {
    private Long id;
    private String name;
    private Long count;

    public RecordStatisticsDto(Long id, String name, Long count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }
}
