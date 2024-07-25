package btongtong.btongtalkback.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CategoryFlashcard {
    @EmbeddedId
    private CategoryFlashcardId id;

    @MapsId("flashcardId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_id")
    private Flashcard flashcard;

    @MapsId("categoryId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}

