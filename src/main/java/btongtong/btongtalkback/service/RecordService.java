package btongtong.btongtalkback.service;

import btongtong.btongtalkback.domain.Flashcard;
import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.domain.Record;
import btongtong.btongtalkback.domain.RecordStatus;
import btongtong.btongtalkback.dto.RecordByStatusDto;
import btongtong.btongtalkback.dto.RecordDto;
import btongtong.btongtalkback.dto.RecordStatisticsDto;
import btongtong.btongtalkback.dto.UpdateRecordStatusDto;
import btongtong.btongtalkback.repository.FlashCardRepository;
import btongtong.btongtalkback.repository.MemberRepository;
import btongtong.btongtalkback.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final MemberRepository memberRepository;
    private final RecordRepository recordRepository;
    private final FlashCardRepository flashCardRepository;

    @Transactional
    public ResponseEntity findRecordByStatus(Long memberId, RecordStatus status, Pageable pageable) {
        Page<RecordByStatusDto> recordByStatus = recordRepository.findRecordByStatus(memberId, status, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(recordByStatus.getContent());
    }

    @Transactional
    public ResponseEntity saveRecord(RecordDto dto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        Flashcard flashcard = flashCardRepository.findById(dto.getFlashcardId()).orElseThrow(IllegalArgumentException::new);
        Record record = Record.builder().member(member).status(dto.getStatus()).flashcard(flashcard).build();

        recordRepository.save(record);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity updateRecord(UpdateRecordStatusDto dto, Long memberId) {
        Record record = recordRepository.findById(dto.getRecordId()).orElseThrow(IllegalArgumentException::new);
        if(!Objects.equals(record.getMember().getId(), memberId)) {
            throw new IllegalArgumentException("no permission.");
        }
        record.updateStatus(dto.getStatus());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity statistics(Long memberId, RecordStatus status) {
        List<RecordStatisticsDto> recordStatistics = recordRepository.findRecordStatistics(memberId, status);
        return ResponseEntity.status(HttpStatus.OK).body(recordStatistics);
    }
}
