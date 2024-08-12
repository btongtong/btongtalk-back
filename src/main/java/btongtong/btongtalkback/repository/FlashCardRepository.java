package btongtong.btongtalkback.repository;

import btongtong.btongtalkback.domain.Flashcard;
import btongtong.btongtalkback.dto.FlashcardCombiDto;
import btongtong.btongtalkback.dto.FlashcardDto;
import btongtong.btongtalkback.dto.SearchFlashcardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FlashCardRepository extends JpaRepository<Flashcard, Long> {
    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.FlashcardDto(f.id, f.question, f.answer) " +
            "FROM Flashcard f " +
            "WHERE f.id NOT IN (SELECT r.flashcard.id FROM Record r WHERE r.member.id = :memberId) " +
            "AND f.category.id = :categoryId")
    List<FlashcardDto> findFlashcard(@Param("memberId") Long memberId, @Param("categoryId") Long categoryId);

    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.FlashcardCombiDto(c.id, c.name, count(f.id)) " +
            "FROM Flashcard f " +
            "LEFT JOIN f.category c " +
            "WHERE c.id = :categoryId " +
            "GROUP BY c.id")
    Optional<FlashcardCombiDto> findFlashcardCategoryAndTotal(@Param("categoryId") Long categoryId);

    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.SearchFlashcardDto(c.name, f.id, f.question, r.status) " +
            "FROM Flashcard f " +
            "LEFT JOIN f.records r " +
            "ON (r.member.id = :memberId OR :memberId IS NULL) " +
            "LEFT JOIN f.category c " +
            "WHERE f.question LIKE %:question%")
    Page<SearchFlashcardDto> findFlashcardByQuestion(@Param("memberId") Long memberId, @Param("question") String question, Pageable pageable);

}
