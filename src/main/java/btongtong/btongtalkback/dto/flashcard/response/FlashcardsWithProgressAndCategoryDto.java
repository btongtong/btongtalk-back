package btongtong.btongtalkback.dto.flashcard.response;

import btongtong.btongtalkback.domain.Category;
import lombok.Getter;

import java.util.List;

@Getter
public class FlashcardsWithProgressAndCategoryDto {
    private String categoryName;
    private String categoryDescription;

    private List<FlashcardWithProgressDto> flashcardList;

    public FlashcardsWithProgressAndCategoryDto(Category category, List<FlashcardWithProgressDto> flashcardList) {
        this.categoryName = category.getName();
        this.categoryDescription = category.getDescription();
        this.flashcardList = flashcardList;
    }

    public FlashcardsWithProgressAndCategoryDto updateFlashcards (List<FlashcardWithProgressDto> flashcardList) {
        this.flashcardList = flashcardList;
        return this;
    }
}
