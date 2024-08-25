package btongtong.btongtalkback.dto.category.response;

import btongtong.btongtalkback.domain.Category;
import btongtong.btongtalkback.dto.category.response.CategoryDto;
import lombok.Getter;

import java.util.List;

@Getter
public class SubCategoryDto {
    private List<CategoryDto> subCategories;
    private String rootName;
    private String rootDescription;

    public SubCategoryDto(List<CategoryDto> subCategories, Category rootCategory) {
        this.subCategories = subCategories;
        this.rootName = rootCategory.getName();
        this.rootDescription = rootCategory.getDescription();
    }
}
