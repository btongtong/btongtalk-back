package btongtong.btongtalkback.dto;

import lombok.Getter;

@Getter
public class CategoryDto {
    private Long id;
    private String name;
    private Long count;

    public CategoryDto(Long id, String name, Long count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }
}
