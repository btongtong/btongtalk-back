package btongtong.btongtalkback.dto.record.response;

import lombok.Getter;

import java.util.List;

@Getter
public class RecordsByStatusWithTotalPages {
    private List<RecordDto> records;
    private int totalPages;

    public RecordsByStatusWithTotalPages(List<RecordDto> records, int totalPages) {
        this.records = records;
        this.totalPages = totalPages;
    }
}
