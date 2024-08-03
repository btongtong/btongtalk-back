package btongtong.btongtalkback.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flashcard {
    @Id @GeneratedValue
    @Column(name = "flashcard_id")
    private Long id;

    private String question;
    private String answer;

    @JsonIgnore
    @OneToMany(mappedBy = "flashcard", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "flashcard", cascade = CascadeType.ALL)
    private List<CategoryFlashcard> categoryFlashcards = new ArrayList<>();

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
