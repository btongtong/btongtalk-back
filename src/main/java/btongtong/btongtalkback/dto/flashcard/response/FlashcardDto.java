package btongtong.btongtalkback.dto.flashcard.response;

import lombok.Getter;

@Getter
public class FlashcardDto {
    private Long id;
    private String question;
    private String answer;

    public FlashcardDto(Long id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }
}
