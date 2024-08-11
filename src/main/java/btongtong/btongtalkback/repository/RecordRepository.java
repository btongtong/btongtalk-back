package btongtong.btongtalkback.repository;

import btongtong.btongtalkback.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
