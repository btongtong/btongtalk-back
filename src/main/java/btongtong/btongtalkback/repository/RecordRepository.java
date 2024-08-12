package btongtong.btongtalkback.repository;

import btongtong.btongtalkback.domain.Record;
import btongtong.btongtalkback.domain.RecordStatus;
import btongtong.btongtalkback.dto.RecordByStatusDto;
import btongtong.btongtalkback.dto.RecordDto;
import btongtong.btongtalkback.dto.RecordStatisticsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.RecordByStatusDto(c.name, f.id, f.question, r.status) " +
            "FROM Record r " +
            "LEFT JOIN r.flashcard f " +
            "LEFT JOIN f.category c " +
            "WHERE r.member.id = :memberId AND r.status = :status")
    Page<RecordByStatusDto> findRecordByStatus(@Param("memberId") Long memberId, @Param("status") RecordStatus status, Pageable pageable);

    @Query(value =
            "SELECT new btongtong.btongtalkback.dto.RecordStatisticsDto(c.parent.id, c.parent.name, COUNT(f.id), " +
            "SUM(CASE WHEN r.status = :status THEN 1 ELSE 0 END)) " +
            "FROM Flashcard f " +
            "LEFT JOIN f.category c " +
            "LEFT JOIN Record r ON r.flashcard.id = f.id AND r.member.id = :memberId " +
            "GROUP BY c.parent.id, c.parent.name")
    List<RecordStatisticsDto> findRecordStatistics(@Param("memberId") Long memberId, @Param("status") RecordStatus status);

}
