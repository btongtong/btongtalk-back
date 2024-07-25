package btongtong.btongtalkback.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CategoryFlashcardId implements Serializable {
    private Long flashcardId;
    private Long categoryId;
}
