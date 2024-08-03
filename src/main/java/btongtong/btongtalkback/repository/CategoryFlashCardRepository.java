package btongtong.btongtalkback.repository;

import btongtong.btongtalkback.domain.CategoryFlashcard;
import btongtong.btongtalkback.domain.CategoryFlashcardId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryFlashCardRepository extends JpaRepository<CategoryFlashcard, CategoryFlashcardId> {
}
