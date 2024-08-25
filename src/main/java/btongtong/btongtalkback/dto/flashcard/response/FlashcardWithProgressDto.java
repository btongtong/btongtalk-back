package btongtong.btongtalkback.dto.flashcard.response;

import lombok.Getter;

@Getter
public class FlashcardWithProgressDto {
    private Long id;
    private String question;
    private String answer;
    private Boolean progress;

    public FlashcardWithProgressDto(Long id, String question, String answer, Boolean progress) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.progress = progress;
    }

}
