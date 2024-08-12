package btongtong.btongtalkback.dto;

import btongtong.btongtalkback.domain.Flashcard;
import lombok.Getter;

import java.util.List;

@Getter
public class FlashcardCombiDto {
    private Long id;
    private String name;
    private Long total;
    private List<FlashcardDto> flashcards;

    public FlashcardCombiDto(Long id, String name, Long total) {
        this.id = id;
        this.name = name;
        this.total = total;
    }

    public FlashcardCombiDto updateFlashcards (List<FlashcardDto> flashcards) {
        this.flashcards = flashcards;
        return this;
    }
}
