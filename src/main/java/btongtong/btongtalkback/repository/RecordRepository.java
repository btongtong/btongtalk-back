package btongtong.btongtalkback.repository;

import btongtong.btongtalkback.domain.Record;
import btongtong.btongtalkback.constant.RecordStatus;
import btongtong.btongtalkback.dto.record.response.RecordDto;
import btongtong.btongtalkback.dto.record.response.RecordStatisticsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.record.response.RecordDto(c.name, f.id, f.question, r.recordDate, r.progress) " +
            "FROM Record r " +
            "LEFT JOIN r.flashcard f " +
            "LEFT JOIN f.category c " +
            "WHERE r.member.id = :memberId AND r.status = :status")
    Page<RecordDto> findRecordsByStatus(@Param("memberId") Long memberId, @Param("status") RecordStatus status, Pageable pageable);

    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.record.response.RecordStatisticsDto(p.id, p.name, COUNT(r.id)) " +
            "FROM Category p " +
            "LEFT JOIN  Category c ON p.id = c.parent.id " +
            "LEFT JOIN Flashcard f ON f.category.id = c.id " +
            "LEFT JOIN Record r ON r.flashcard.id = f.id AND r.member.id = :memberId AND r.status = :status " +
            "GROUP BY p.id, p.name")
    List<RecordStatisticsDto> findRecordStatistics(@Param("memberId") Long memberId, @Param("status") RecordStatus status);

    Optional<Record> findByMemberIdAndFlashcardId(Long memberId, Long flashcardId);

    @Modifying
    @Query("UPDATE Record r SET r.progress = :progress WHERE r.member.id = :memberId AND r.flashcard.category.id = :categoryId")
    void updateProgress(Long memberId, Long categoryId, Boolean progress);
}
