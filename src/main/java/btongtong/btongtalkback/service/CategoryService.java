package btongtong.btongtalkback.service;

import btongtong.btongtalkback.constant.ErrorCode;
import btongtong.btongtalkback.domain.Category;
import btongtong.btongtalkback.dto.category.response.CategoryDto;
import btongtong.btongtalkback.dto.category.response.SubCategoryDto;
import btongtong.btongtalkback.handler.exception.CustomException;
import btongtong.btongtalkback.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getRootWithChildrenCnt() {
        return categoryRepository.findRootsWithChildrenCnt();
    }

    public SubCategoryDto getSubWithFlashcardCntAndRoot(Long categoryId) {
        List<CategoryDto> subCategories = categoryRepository.findSubsWithFlashcardCnt(categoryId);
        Category rootCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        return new SubCategoryDto(subCategories, rootCategory);
    }
}
