package btongtong.btongtalkback.repository;

import btongtong.btongtalkback.domain.Category;
import btongtong.btongtalkback.dto.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT new btongtong.btongtalkback.dto.CategoryDto(c.id, c.name, COUNT(cf)) " +
            "FROM Category c " +
            "LEFT JOIN c.categoryFlashcards cf " +
            "WHERE c.depth = :depth " +
            "GROUP BY c.id, c.name")
    List<CategoryDto> findCategoriesByDepth (int depth);
}
