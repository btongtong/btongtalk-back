package btongtong.btongtalkback.dto.record.request;

import lombok.Getter;

@Getter
public class CategoryIdAndProgressDto {
    private Long categoryId;
    private Boolean progress;

    public CategoryIdAndProgressDto(Long categoryId, Boolean progress) {
        this.categoryId = categoryId;
        this.progress = progress;
    }
}
