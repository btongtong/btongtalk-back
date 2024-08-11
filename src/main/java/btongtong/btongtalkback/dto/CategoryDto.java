package btongtong.btongtalkback.dto;

import lombok.Getter;

@Getter
public class CategoryDto {
    private Long categoryId;
    private String name;
    private Long count;

    public CategoryDto(Long categoryId, String name, Long count) {
        this.categoryId = categoryId;
        this.name = name;
        this.count = count;
    }
}
