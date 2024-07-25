package btongtong.btongtalkback.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Flashcard {
    @Id @GeneratedValue
    @Column(name = "flashcard_id")
    private Long id;

    @OneToMany(mappedBy = "flashcard", cascade = CascadeType.ALL)
    private List<CategoryFlashcard> categoryFlashcards = new ArrayList<>();

    private String question;
    private String answer;

    @JsonIgnore
    @OneToMany(mappedBy = "flashcard")
    private List<Record> records = new ArrayList<>();

}
