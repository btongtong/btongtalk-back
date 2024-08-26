package btongtong.btongtalkback.repository;

import btongtong.btongtalkback.domain.Record;
import btongtong.btongtalkback.constant.RecordStatus;
import btongtong.btongtalkback.dto.record.response.RecordDto;
import btongtong.btongtalkback.dto.record.response.RecordStatisticsByFlashcardDto;
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
            "SELECT new btongtong.btongtalkback.dto.record.response.RecordDto(c.name, f.id, f.question, r.recordDate, r.status) " +
            "FROM Record r " +
            "LEFT JOIN r.flashcard f " +
            "LEFT JOIN f.category c " +
            "WHERE r.member.id = :memberId AND r.status = :status")
    Page<RecordDto> findRecordsByStatus(@Param("memberId") Long memberId, @Param("status") RecordStatus status, Pageable pageable);

    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.record.response.RecordStatisticsDto(p.id, p.name, COUNT(r.id)) " +
            "FROM Category p " +
            "LEFT JOIN  p.children c " +
            "LEFT JOIN c.flashcards f " +
            "LEFT JOIN Record r ON r.flashcard.id = f.id AND r.member.id = :memberId AND r.status = :status " +
            "WHERE p.parent IS NULL " +
            "GROUP BY p.id, p.name")
    List<RecordStatisticsDto> findRecordStatistics(@Param("memberId") Long memberId, @Param("status") RecordStatus status);

    Optional<Record> findByMemberIdAndFlashcardId(Long memberId, Long flashcardId);

    @Modifying
    @Query("UPDATE Record r SET r.progress = :progress WHERE r.member.id = :memberId AND r.flashcard.category.id = :categoryId")
    void updateProgress(Long memberId, Long categoryId, Boolean progress);

    @Query("SELECT new btongtong.btongtalkback.dto.record.response.RecordStatisticsByFlashcardDto( " +
            "SUM(CASE WHEN r.status = 'KNOWN' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN r.status = 'UNKNOWN' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN r.status = 'NORECORD' OR r.status IS NULL THEN 1 ELSE 0 END)) " +
            "FROM Flashcard f " +
            "LEFT JOIN Record r ON f.id = r.flashcard.id " +
            "WHERE f.category.id = :categoryId AND r.member.id = :memberId")
    RecordStatisticsByFlashcardDto findRecordStatisticsByStatus(Long memberId, Long categoryId);

}
