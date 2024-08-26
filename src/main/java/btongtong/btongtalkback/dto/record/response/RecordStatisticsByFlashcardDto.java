package btongtong.btongtalkback.dto.record.response;

import lombok.Getter;

@Getter
public class RecordStatisticsByFlashcardDto {
    private Long knownCnt;
    private Long unknownCnt;
    private Long restCnt;

    public RecordStatisticsByFlashcardDto(Long knownCnt, Long unKnownCnt, Long restCnt) {
        this.knownCnt = knownCnt;
        this.unknownCnt = unKnownCnt;
        this.restCnt = restCnt;
    }
}
