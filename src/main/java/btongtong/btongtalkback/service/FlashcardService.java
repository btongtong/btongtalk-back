package btongtong.btongtalkback.service;

import btongtong.btongtalkback.dto.FlashcardCombiDto;
import btongtong.btongtalkback.dto.SearchFlashcardDto;
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

    @Transactional
    public ResponseEntity getFlashcardList(Long categoryId, Long memberId) {
        FlashcardCombiDto flashcardCombi = flashCardRepository.findFlashcardCategoryAndTotal(categoryId).orElseThrow(IllegalArgumentException::new)
                .updateFlashcards(flashCardRepository.findFlashcard(memberId, categoryId));

        return ResponseEntity.status(HttpStatus.OK).body(flashcardCombi);
    }

    @Transactional
    public ResponseEntity searchFlashcard(Long memberId, String question, Pageable pageable) {
        Page<SearchFlashcardDto> flashcardByQuestion = flashCardRepository.findFlashcardByQuestion(memberId, question, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(flashcardByQuestion.getContent());
    }
}
