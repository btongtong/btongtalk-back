package btongtong.btongtalkback.repository;

import btongtong.btongtalkback.domain.Flashcard;
import btongtong.btongtalkback.dto.flashcard.response.FlashcardWithProgressDto;
import btongtong.btongtalkback.dto.flashcard.response.SearchFlashcardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlashCardRepository extends JpaRepository<Flashcard, Long> {

    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.flashcard.response.FlashcardWithProgressDto(f.id, f.question, f.answer, r.progress) " +
            "FROM Flashcard f " +
            "LEFT JOIN Record r ON r.member.id = :memberId AND r.flashcard.id = f.id " +
            "WHERE f.category.id = :categoryId")
    List<FlashcardWithProgressDto> findFlashcardWithProgress(@Param("memberId") Long memberId, @Param("categoryId") Long categoryId);

    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.flashcard.response.SearchFlashcardDto(c.name, f.id, f.question, r.status) " +
            "FROM Flashcard f " +
            "LEFT JOIN f.records r " +
            "ON (r.member.id = :memberId OR :memberId IS NULL) " +
            "LEFT JOIN f.category c " +
            "WHERE f.question LIKE %:question%")
    Page<SearchFlashcardDto> findFlashcardsByQuestion(@Param("memberId") Long memberId, @Param("question") String question, Pageable pageable);
}
