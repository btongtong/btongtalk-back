package btongtong.btongtalkback.dto.flashcard.response;

import btongtong.btongtalkback.domain.Flashcard;
import lombok.Getter;

@Getter
public class FlashcardWithCategoryDto {
    private Long id;
    private String question;
    private String answer;
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;

    public FlashcardWithCategoryDto(Flashcard flashcard) {
        this.id = flashcard.getId();
        this.question = flashcard.getQuestion();
        this.answer = flashcard.getAnswer();
        this.categoryId = flashcard.getCategory().getId();
        this.categoryName = flashcard.getCategory().getName();
        this.categoryDescription = flashcard.getCategory().getDescription();;
    }
}
