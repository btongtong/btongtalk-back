package btongtong.btongtalkback.controller;

import btongtong.btongtalkback.dto.category.response.SubCategoryDto;
import btongtong.btongtalkback.dto.category.response.CategoryDto;
import btongtong.btongtalkback.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> rootCategories() {
        List<CategoryDto> response = categoryService.getRootWithChildrenCnt();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<?> subCategories(@PathVariable("categoryId") Long categoryId) {
        SubCategoryDto response = categoryService.getSubWithFlashcardCntAndRoot(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
