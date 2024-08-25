package btongtong.btongtalkback.service;

import btongtong.btongtalkback.domain.Category;
import btongtong.btongtalkback.domain.Flashcard;
import btongtong.btongtalkback.dto.flashcard.response.*;
import btongtong.btongtalkback.repository.CategoryRepository;
import btongtong.btongtalkback.repository.FlashCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlashcardService {
    private final FlashCardRepository flashCardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public FlashcardsWithProgressAndCategoryDto getFlashcardsWithProgressAndCategory (Long categoryId, Long memberId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
        List<FlashcardWithProgressDto> flashcards = flashCardRepository.findFlashcardWithProgress(memberId, categoryId);
        return new FlashcardsWithProgressAndCategoryDto(category, flashcards);
    }

    @Transactional
    public FlashcardWithCategoryDto getFlashcard(Long flashcardId) {
        Flashcard flashcard = flashCardRepository.findById(flashcardId).orElseThrow(IllegalArgumentException::new);
        return new FlashcardWithCategoryDto(flashcard);
    }

    @Transactional
    public SearchFlashcardsWithTotalPagesDto searchFlashcards(Long memberId, String question, Pageable pageable) {
        Page<SearchFlashcardDto> flashcardsByQuestion = flashCardRepository.findFlashcardsByQuestion(memberId, question, pageable);
        return new SearchFlashcardsWithTotalPagesDto(flashcardsByQuestion.getContent(), flashcardsByQuestion.getTotalPages());
    }
}
