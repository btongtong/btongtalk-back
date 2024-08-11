package btongtong.btongtalkback.service;

import btongtong.btongtalkback.dto.CategoryDto;
import btongtong.btongtalkback.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ResponseEntity findRootCategories() {
        List<CategoryDto> rootCategories =  categoryRepository.findRootCategories(1);
        return ResponseEntity.status(HttpStatus.OK).body(rootCategories);
    }

    public ResponseEntity findCategories(int categoryId) {
        List<CategoryDto> categories = categoryRepository.findCategories(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}
