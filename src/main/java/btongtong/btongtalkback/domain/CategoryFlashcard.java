package btongtong.btongtalkback.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public CategoryFlashcard(Flashcard flashcard, Category category) {
        this.flashcard = flashcard;
        this.category = category;

        flashcard.getCategoryFlashcards().add(this);
        category.getCategoryFlashcards().add(this);
    }
}

