package btongtong.btongtalkback.dto.category.response;

import lombok.Getter;

@Getter
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private Long count;

    public CategoryDto(Long id, String name, String description, Long count) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.count = count;
    }
}
