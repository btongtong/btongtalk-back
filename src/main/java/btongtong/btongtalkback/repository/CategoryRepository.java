package btongtong.btongtalkback.repository;

import btongtong.btongtalkback.domain.Category;
import btongtong.btongtalkback.dto.category.response.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.category.response.CategoryDto(p.id, p.name, p.description, COUNT(c.id)) " +
            "FROM Category p " +
            "LEFT JOIN p.children c " +
            "WHERE p.depth = :depth " +
            "GROUP BY p.id, p.name")
    List<CategoryDto> findRootsWithChildrenCnt (@Param("depth") int depth);

    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.category.response.CategoryDto(c.id, c.name, c.description, COUNT(f.id)) " +
            "FROM Category c " +
            "LEFT JOIN c.flashcards f " +
            "WHERE c.parent.id = :parentId " +
            "GROUP BY c.id, c.name")
    List<CategoryDto> findSubsWithFlashcardCnt (@Param("parentId") Long parentId);

}
