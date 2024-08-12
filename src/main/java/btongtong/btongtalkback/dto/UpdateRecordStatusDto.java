package btongtong.btongtalkback.dto;

import btongtong.btongtalkback.domain.RecordStatus;
import lombok.Getter;

@Getter
public class UpdateRecordStatusDto {
    private Long recordId;
    private RecordStatus status;

    public UpdateRecordStatusDto(Long recordId, RecordStatus status) {
        this.recordId = recordId;
        this.status = status;
    }
}
