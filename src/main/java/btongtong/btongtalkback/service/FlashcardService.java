package btongtong.btongtalkback.service;

import btongtong.btongtalkback.repository.FlashCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlashcardService {
    private final FlashCardRepository flashCardRepository;

    public ResponseEntity getFlashcardList(Long categoryId) {

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
