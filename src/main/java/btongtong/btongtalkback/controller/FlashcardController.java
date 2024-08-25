package btongtong.btongtalkback.controller;

import btongtong.btongtalkback.dto.auth.AuthDto;
import btongtong.btongtalkback.dto.flashcard.response.FlashcardWithCategoryDto;
import btongtong.btongtalkback.dto.flashcard.response.FlashcardsWithProgressAndCategoryDto;
import btongtong.btongtalkback.dto.flashcard.response.SearchFlashcardsWithTotalPagesDto;
import btongtong.btongtalkback.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FlashcardController {
    private final FlashcardService flashcardService;

    @GetMapping("/categories/{categoryId}/flashcards")
    public ResponseEntity<?> FlashcardsWithProgressAndCategory (@PathVariable("categoryId") Long categoryId, @AuthenticationPrincipal AuthDto memberDto) {
        FlashcardsWithProgressAndCategoryDto response = flashcardService.getFlashcardsWithProgressAndCategory(categoryId, memberDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/flashcards")
    public ResponseEntity<?> searchFlashcards (@RequestParam("question") String question,
                                             @AuthenticationPrincipal AuthDto authDto,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "2") int size) {
        Long memberId = (authDto != null) ? authDto.getId() : null;
        Pageable pageable = PageRequest.of(page, size);
        SearchFlashcardsWithTotalPagesDto response = flashcardService.searchFlashcards(memberId, question, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/flashcards/{flashcardId}")
    public ResponseEntity<?> flashcard (@PathVariable("flashcardId") Long flashcardId) {
        FlashcardWithCategoryDto response = flashcardService.getFlashcard(flashcardId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
