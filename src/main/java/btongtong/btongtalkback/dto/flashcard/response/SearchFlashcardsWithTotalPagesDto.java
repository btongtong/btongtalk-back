package btongtong.btongtalkback.dto.flashcard.response;

import lombok.Getter;

import java.util.List;

@Getter
public class SearchFlashcardsWithTotalPagesDto {
    private List<SearchFlashcardDto> flashcards;
    private int totalPages;

    public SearchFlashcardsWithTotalPagesDto(List<SearchFlashcardDto> flashcards, int totalPages) {
        this.flashcards = flashcards;
        this.totalPages = totalPages;
    }
}
