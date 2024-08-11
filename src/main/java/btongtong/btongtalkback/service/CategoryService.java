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

    public ResponseEntity findCategories(int depth) {
        List<CategoryDto> categories = categoryRepository.findCategoriesByDepth(depth);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}
